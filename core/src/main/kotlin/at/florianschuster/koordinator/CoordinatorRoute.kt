package at.florianschuster.koordinator

/**
 * An object defining the routes that are possible results within or through a Coordinator.
 *
 * It is not up to the [CoordinatorRoute] to decide where or how to navigate! Do not couple view flows to a
 * route, rather try to define a coordinated action as route.
 * e.g. "ShowIsPicked(show: Show)" instead of "OpenShowDetailViewWith(show: Show)"
 *
 * Each screen should have one [CoordinatorRoute] object (this could be an enum or a sealed class).
 */
interface CoordinatorRoute