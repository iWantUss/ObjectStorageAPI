package storage

import components.Component
import components.Info
import storage.cloud.YandexObjectStorage
import storage.cloud.YandexObjectStorageConfig
import storage.local.LocalStorage

class ComponentsStorage(storageConfig: StorageConfig = StorageConfig()) : Storage {
    private val componentsStorage = HashMap<String, Info>()

    val yandexObjectStorage = YandexObjectStorage(storageConfig.backet, storageConfig.yandexConfig)
    val localStorage = LocalStorage(storageConfig.basePathLocalHost)

    override fun pull(key: String): Component? {
        val info = componentsStorage[key]
        if (info != null) {
            return Component(key, componentsStorage[key]!!)
        }

        val localComponent = localStorage.pull(key)
        if (localComponent != null) {
            componentsStorage[key] = localComponent.info
            return Component(key, componentsStorage[key]!!)
        }

        val yandexComponent = yandexObjectStorage.pull(key) ?: return null
        localStorage.push(yandexComponent)
        componentsStorage[key] = yandexComponent.info
        return Component(key, componentsStorage[key]!!)
    }

    override fun push(component: Component) {
        componentsStorage[component.key] = component.info
        localStorage.push(component)
        yandexObjectStorage.push(component)
    }

    override fun delete(component: Component) {
        componentsStorage.remove(component.key)
        localStorage.delete(component)
        yandexObjectStorage.delete(component)
    }
}

data class StorageConfig(
    val yandexConfig: YandexObjectStorageConfig = YandexObjectStorageConfig(
        "",
        ""
    ),
    val backet: String = "",
    val basePathLocalHost: String = "/tmp/"
)
