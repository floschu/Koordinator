package at.florianschuster.koordinator

import android.app.Activity
import android.view.ViewGroup
import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import java.lang.ClassCastException

/**
 * A [Coordinator] handles navigation or view flow for one or more view controllers (e.g. [Fragment],
 * [Activity], [ViewGroup], [View]). Its purpose is to isolate navigation logic.
 *
 * The [CoordinatorRoute] defines the routes that the coordinator can navigate to with the help of a
 * [NavigationHandler].
 */
abstract class Coordinator<CoordinatorRoute, NavigationHandler> where CoordinatorRoute : Route, NavigationHandler : Any {
    private val disposables = CompositeDisposable()
    private var handler: NavigationHandler? = null

    init {
        Router.routes
            .subscribe { route ->
                @Suppress("UNCHECKED_CAST")
                val coordinatorRoute: CoordinatorRoute = route as CoordinatorRoute

                handler?.let { navHandler ->
                    try {
                        navigate(coordinatorRoute, navHandler)
                    } catch (cce: ClassCastException) {
                        // todo can this be done differently?
                        // route is not of type CoordinatorRoute --> don't navigate
                    }
                }
            }
            .let(disposables::add)
    }

    /**
     * Attaches a navigation handler to this [Coordinator] that is used to handle navigation.
     */
    infix fun provideNavigationHandler(handler: NavigationHandler) {
        this.handler = handler
    }

    /**
     * Method that handles the navigation that is defined through a [CoordinatorRoute].
     */
    abstract fun navigate(route: CoordinatorRoute, handler: NavigationHandler)

    @CallSuper
    open fun onCleared() {
        disposables.clear()
        handler = null
    }
}