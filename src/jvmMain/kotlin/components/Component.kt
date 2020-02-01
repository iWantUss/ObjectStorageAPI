package components

data class Component(
    val key: String,
    val info: Info
) {
    override fun hashCode(): Int {
        return key.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Component
        if (key != other.key) return false

        return true
    }
}
