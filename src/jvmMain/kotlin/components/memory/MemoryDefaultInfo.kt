package components.memory

import components.Info
import components.StateInfo

class MemoryDefaultInfo(private val defaultInfo: Info, var isNew: Boolean = false) : Info by defaultInfo, StateInfo {

    companion object {
        fun updateModificationTime(component: MemoryDefaultInfo) {
            component.modificationTime = System.currentTimeMillis()
        }
    }

    var creationTime: Long = if (isNew) 0 else System.currentTimeMillis()
    var modificationTime: Long = creationTime

    override fun setComponents(componentsList: List<String>) {
        updateModificationTime(this)
        defaultInfo.setComponents(componentsList)
    }

    override fun setContent(contentData: String) {
        updateModificationTime(this)
        defaultInfo.setContent(contentData)
    }

    override fun sync() {
        if (isNew) {
            creationTime = System.currentTimeMillis()
            isNew = false
        }
        modificationTime = creationTime
    }

    override fun isSync(): Boolean {
        return !isNew && !isModified()
    }

    override fun isModified(): Boolean {
        return creationTime != modificationTime
    }
}
