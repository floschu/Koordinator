package at.florianschuster.koorinatorandroid

import androidx.lifecycle.LifecycleOwner

/**
 * Lazily creates a [LifecycleCoordinator] and adds it as observer to the lifecycle of the [LifecycleOwner].
 */
fun <C : LifecycleCoordinator<*, *>> LifecycleOwner.coordinator(factory: () -> C): Lazy<C> {
    return lazy { factory().also { lifecycle.addObserver(it) } }
}