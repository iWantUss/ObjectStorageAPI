package storage

interface Storage {
    fun pull(path: String): Any

    fun push(obj: Any)

    fun commit()

    fun delete(path: String): Boolean

}
