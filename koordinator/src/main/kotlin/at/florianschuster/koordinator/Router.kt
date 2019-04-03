package at.florianschuster.koordinator

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * The [Router] can be used in a view independent class to follow a specific [CoordinatorRoute].
 * A flow specific [Coordinator] then uses the [Router] to determine where to navigate.
 */
object Router {
    private val routeSubject: PublishSubject<CoordinatorRoute> = PublishSubject.create<CoordinatorRoute>()

    /**
     * This is used to observe which [CoordinatorRoute] to handle in a [Coordinator].
     */
    val routes: Observable<CoordinatorRoute> = routeSubject.hide()

    /**
     * Use this to follow a specific [CoordinatorRoute].
     */
    infix fun <RouteToFollow : CoordinatorRoute> follow(route: RouteToFollow) = routeSubject.onNext(route)
}