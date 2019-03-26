package at.florianschuster.koorinatorandroid

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import at.florianschuster.koordinator.Coordinator
import at.florianschuster.koordinator.KoordinatorRoute
import at.florianschuster.koordinator.Router

/**
 * A [Coordinator] that also accepts a [LifecycleOwner] and binds to its [Lifecycle] to clear its resources.
 */
abstract class LifecycleCoordinator<Route : KoordinatorRoute, NavigationHandler : Any>(
    router: Router,
    lifecycleOwner: LifecycleOwner
) : Coordinator<Route, NavigationHandler>(router), LifecycleObserver {

    init {
        @Suppress("LeakingThis")
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected fun onDestroy() {
        onCleared()
    }
}