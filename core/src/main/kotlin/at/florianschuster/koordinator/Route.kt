package at.florianschuster.koordinator

/**
 * A [Route] expresses a way that a Coordinator can follow for navigation.
 *
 * It is not up to the [Route] to decide where or how to navigate. Do not couple view flows to a
 * [Route], rather try to define a coordinated action as [Route].
 * e.g. "ShowIsPicked(show: Show)" instead of "OpenShowDetailViewWith(show: Show)"
 *
 * Each screen should have one [Route] object (this could be an enum or a sealed class).
 */
interface Route