package at.florianschuster.koordinator

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

/**
 * A [Router] can be injected into a View independent class and used to follow a specific [KoordinatorRoute].
 * A flow specific [Coordinator] then uses the router to determine where to navigate.
 */
object Router {
    private val routeSubject: PublishSubject<KoordinatorRoute> = PublishSubject.create<KoordinatorRoute>()

    /**
     * Use this to observe which [KoordinatorRoute] to handle.
     */
    val routes: Flowable<KoordinatorRoute> = routeSubject.toFlowable(BackpressureStrategy.LATEST)

    infix fun follow(route: KoordinatorRoute) {
        routeSubject.onNext(route)
    }
}