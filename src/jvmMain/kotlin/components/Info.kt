package components

interface Info {

    fun getComponents(): List<String>

    fun getContent(): String

    fun setComponents(componentsList: List<String>)

    fun setContent(contentData: String)
}
