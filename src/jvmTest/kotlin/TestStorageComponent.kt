import components.StateInfo
import org.junit.BeforeClass
import org.junit.Test
import storage.ComponentsStorage
import kotlin.test.assertFalse


class TestStorageComponent {
    companion object {
        lateinit var componentsStorage: ComponentsStorage

        @BeforeClass
        @JvmStatic
        fun setup() {
            componentsStorage = ComponentsStorage()
        }
    }


    @Test
    fun testPullUnknownComponent() {
        val testRootComponent = componentsStorage.pull("UnknownComponent")
        val stateInfo = testRootComponent.info as StateInfo

        assertFalse(stateInfo.isSync())
    }

    @Test
    fun testModificationUnknownComponent() {
        val testRootComponent = componentsStorage.pull("UnknownComponent")
        testRootComponent.info.setComponents(listOf("someComponent"))
        val stateInfo = testRootComponent.info as StateInfo

        assertFalse(stateInfo.isSync())
        assert(stateInfo.isModified())
    }


    @Test
    fun testPushUnknownComponent() {
        val testRootComponent = componentsStorage.pull("UnknownComponent")
        componentsStorage.push(testRootComponent)

        val stateInfo = testRootComponent.info as StateInfo
        assert(stateInfo.isSync())
        assertFalse(stateInfo.isModified())

        componentsStorage.delete(testRootComponent)
    }

    @Test
    fun testPushModificationUnknownComponent() {
        val testRootComponent = componentsStorage.pull("UnknownComponent")
        testRootComponent.info.setComponents(listOf("someComponent"))
        componentsStorage.push(testRootComponent)

        val stateInfo = testRootComponent.info as StateInfo
        assert(stateInfo.isSync())
        assertFalse(stateInfo.isModified())

        componentsStorage.delete(testRootComponent)
    }
}
