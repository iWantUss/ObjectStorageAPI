package storage.local

import com.google.gson.Gson
import components.Component
import components.DefaultInfo
import components.Info
import storage.Storage
import java.io.File


class LocalStorage(private var basePath: String) : Storage {

    init {
        if (!basePath.endsWith("/")) {
            basePath += "/"
        }
        val directory = File(basePath)
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }


    override fun pull(key: String): Component? {
        val file = File(basePath + key)
        if (file.exists()) {
            return Component(
                key,
                Gson().fromJson(file.reader(), DefaultInfo::class.java)
            )
        }
        return null
    }

    override fun push(component: Component) {
        File(basePath + component.key).writeText(Gson().toJson(component.info))
    }

    override fun delete(component: Component) {
        File(basePath + component.key).delete()
    }

}
