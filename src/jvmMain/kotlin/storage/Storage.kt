package storage

import components.Component

interface Storage {
    fun pull(key: String): Component?


    fun push(component: Component)
    fun delete(component: Component)
}
