package storage

import components.Component
import components.DefaultInfo
import components.StateInfo
import components.memory.MemoryDefaultInfo
import storage.cloud.YandexObjectStorage
import storage.cloud.YandexObjectStorageConfig
import storage.local.LocalStorage

class ComponentsStorage(private val storageConfig: StorageConfig = StorageConfig()) : Storage {
    private val componentsStorage = HashMap<String, MemoryDefaultInfo>()

    val yandexObjectStorage = YandexObjectStorage(storageConfig.backet, storageConfig.yandexConfig)
    val localStorage = LocalStorage(storageConfig.basePathLocalHost)

    override fun pull(key: String): Component {
        val info = componentsStorage[key]
        if (info != null) {
            return Component(key, componentsStorage[key]!!)
        }

        val localComponent = localStorage.pull(key)
        if (localComponent != null) {
            componentsStorage[key] = MemoryDefaultInfo(localComponent.info)
            return Component(key, componentsStorage[key]!!)
        }

        val yandexComponent = yandexObjectStorage.pull(key) ?: return Component(key, MemoryDefaultInfo(DefaultInfo(), true))
        localStorage.push(yandexComponent)
        componentsStorage[key] = MemoryDefaultInfo(yandexComponent.info)
        return Component(key, componentsStorage[key]!!)
    }

    override fun push(component: Component) {
        val stateInfo = component.info as StateInfo
        if (!stateInfo.isSync()) {
            localStorage.push(component)
            yandexObjectStorage.push(component)
            stateInfo.sync()
        }
        componentsStorage[component.key] = MemoryDefaultInfo(component.info)
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
