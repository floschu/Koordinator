package at.florianschuster.koordinator.android

import androidx.lifecycle.LifecycleOwner

/**
 * Lazily creates a [LifecycleCoordinator] and adds it as observer to the lifecycle of the [LifecycleOwner].
 */
fun <C> LifecycleOwner.lifecycleCoordinator(
    factory: () -> C
): Lazy<C> where C : LifecycleCoordinator<*, *> {
    return lazy { factory().also(lifecycle::addObserver) }
}