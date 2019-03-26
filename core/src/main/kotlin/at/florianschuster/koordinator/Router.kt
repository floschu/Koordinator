package at.florianschuster.koordinator

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

/**
 * A [Router] can be injected into a View independent class and used to follow a specific [CoordinatorRoute].
 * A flow specific [Coordinator] then uses the router to determine where to navigate.
 *
 * An application should only have one [Router].
 */
class Router {
    private val routeSubject: PublishSubject<CoordinatorRoute> = PublishSubject.create<CoordinatorRoute>()

    /**
     * Use this to observe which [CoordinatorRoute] to handle.
     */
    val routes: Flowable<CoordinatorRoute> = routeSubject.toFlowable(BackpressureStrategy.LATEST)

    infix fun follow(route: CoordinatorRoute) {
        routeSubject.onNext(route)
    }
}