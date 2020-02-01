package components

class DefaultInfo(
    private val components : List<String> = emptyList(),
    private val content : String = ""
) : Info {
    override fun getComponents(): List<String> {
        return components
    }

    override fun getContent(): String {
        return content
    }
}
