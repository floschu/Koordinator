package at.florianschuster.koordinator

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * The [Router] can be used in a view independent class to follow a specific [Route].
 * A flow specific [Coordinator] then uses the [Router] to determine where to navigate.
 */
object Router {
    private val routeSubject: PublishSubject<Route> = PublishSubject.create<Route>()

    /**
     * This is used to observe which [Route] to handle in a [Coordinator].
     */
    val routes: Observable<Route> = routeSubject.hide()

    /**
     * Use this to follow a specific [Route].
     */
    infix fun <RouteToFollow : Route> follow(route: RouteToFollow) = routeSubject.onNext(route)
}