package at.florianschuster.koordinator.android

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import at.florianschuster.koordinator.Coordinator
import at.florianschuster.koordinator.CoordinatorRoute

/**
 * A [Coordinator] that implements [LifecycleObserver] and clears its resources when the observed [Lifecycle] calls
 * [Lifecycle.Event.ON_DESTROY].
 */
abstract class LifecycleCoordinator<Route, NavigationHandler> : Coordinator<Route, NavigationHandler>(),
    LifecycleObserver where Route : CoordinatorRoute, NavigationHandler : Any {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected fun onDestroy() {
        onCleared()
    }
}