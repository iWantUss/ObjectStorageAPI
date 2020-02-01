package components

interface StateInfo {

    fun isSync(): Boolean

    fun isModified(): Boolean

    fun sync()
}
