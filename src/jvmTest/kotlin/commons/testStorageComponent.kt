package commons

import components.Component
import components.DefaultInfo
import org.junit.Test
import storage.ComponentsStorage


class testStorageComponent {


    @Test
    fun testPushAndPullComponent() {
        val componentsStorage = ComponentsStorage()
        val testRootComponent = Component("root", DefaultInfo(listOf("sub"), "info"))

        componentsStorage.push(testRootComponent)

        assert(componentsStorage.pull(testRootComponent.key)!!.equals(testRootComponent))
        assert(componentsStorage.localStorage.pull(testRootComponent.key)!!.equals(testRootComponent))
        assert(componentsStorage.yandexObjectStorage.pull(testRootComponent.key)!!.equals(testRootComponent))
    }

    @Test
    fun testDeleteComponent() {
        val componentsStorage = ComponentsStorage()
        val testRootComponent = Component("root", DefaultInfo(listOf("sub"), "info"))

        componentsStorage.delete(testRootComponent)

        assert(componentsStorage.pull(testRootComponent.key) == null)
        assert(componentsStorage.localStorage.pull(testRootComponent.key) == null)
        assert(componentsStorage.yandexObjectStorage.pull(testRootComponent.key) == null)
    }
}
