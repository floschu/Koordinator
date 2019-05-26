package at.florianschuster.koordinator.android.koin

import android.content.ComponentCallbacks
import androidx.annotation.RestrictTo
import androidx.lifecycle.LifecycleOwner
import at.florianschuster.koordinator.Coordinator
import at.florianschuster.koordinator.android.LifecycleCoordinator
import org.koin.android.ext.android.getKoin
import org.koin.core.KoinComponent
import org.koin.core.context.GlobalContext
import org.koin.core.definition.BeanDefinition
import org.koin.core.definition.Definition
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope

/**
 * [Coordinator] DSL extension to declare a [Coordinator] in a Koin [Module].
 */
inline fun <reified C> Module.coordinator(
    qualifier: Qualifier? = null,
    override: Boolean = false,
    noinline definition: Definition<C>
): BeanDefinition<C> where C : Coordinator<*, *> = factory(qualifier, override, definition)

/**
 * Lazily gets a [Coordinator] instance for a [LifecycleOwner].
 */
inline fun <L, reified C> L.coordinator(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<C> where L : LifecycleOwner, C : LifecycleCoordinator<*, *> = lazy {
    getKoin().get<C>(qualifier, parameters).also(lifecycle::addObserver)
}

/**
 * Gets a [Coordinator] instance for a [LifecycleOwner].
 */
inline fun <L, reified C> L.getCoordinator(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): C where L : LifecycleOwner, C : LifecycleCoordinator<*, *> {
    return getKoin().get<C>(qualifier, parameters).also(lifecycle::addObserver)
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun LifecycleOwner.getKoin() = when (this) {
    is KoinComponent -> this.getKoin()
    is ComponentCallbacks -> (this as ComponentCallbacks).getKoin()
    else -> GlobalContext.get().koin
}