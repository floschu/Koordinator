package at.florianschuster.koordinator

import android.app.Activity
import android.view.ViewGroup
import androidx.annotation.CallSuper
import io.reactivex.Maybe
import io.reactivex.disposables.CompositeDisposable

/**
 * A [Coordinator] handles navigation or view flow for one or more view controllers (e.g. [Fragment],
 * [Activity], [ViewGroup], [View],...). Its purpose is to isolate navigation logic.
 *
 * The [Route] defines the routes that the coordinator can navigate to with the help of a
 * [NavigationHandler].
 */
abstract class Coordinator<Route, NavigationHandler> where Route : CoordinatorRoute, NavigationHandler : Any {
    private val disposables = CompositeDisposable()
    private var handler: NavigationHandler? = null

    init {
        Router.routes
            .flatMapMaybe {
                @Suppress("UNCHECKED_CAST")
                val route: Route? = (it as? Route)
                val handler: NavigationHandler? = handler

                if (route != null && handler != null) {
                    Maybe.just(route to handler)
                } else {
                    Maybe.empty()
                }
            }
            .subscribe { navigate(it.first, it.second) }
            .let(disposables::add)
    }

    /**
     * Attaches a navigation handler to this [Coordinator] that is used to handle navigation.
     */
    infix fun provideNavigationHandler(handler: NavigationHandler) {
        this.handler = handler
    }

    /**
     * Method that handles the navigation that is defined through a [Route].
     */
    abstract fun navigate(route: Route, handler: NavigationHandler)

    @CallSuper
    open fun onCleared() {
        disposables.clear()
        handler = null
    }
}