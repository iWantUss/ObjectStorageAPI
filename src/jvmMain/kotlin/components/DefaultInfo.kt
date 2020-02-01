package components

open class DefaultInfo(
    private var components: List<String> = emptyList(),
    private var content: String = ""
) : Info {

    override fun getComponents(): List<String> {
        return components
    }

    override fun getContent(): String {
        return content
    }

    override fun setComponents(componentsList: List<String>) {
        components = componentsList
    }

    override fun setContent(contentData: String) {
        content = contentData
    }
}
