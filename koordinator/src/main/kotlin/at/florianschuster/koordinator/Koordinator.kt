package at.florianschuster.koordinator

import androidx.annotation.RestrictTo

/**
 * Object to handle library initializations.
 */
object Koordinator {
    private var errorHandler: ((Throwable) -> Unit)? = null

    /**
     * Attaches an error handler to the library.
     *
     * @param handler (Throwable) -> Unit
     */
    fun handleErrorsWith(handler: ((Throwable) -> Unit)? = null) {
        this.errorHandler = handler
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun handleError(throwable: Throwable) {
        errorHandler?.invoke(throwable)
    }
}