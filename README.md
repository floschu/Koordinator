# Koordinator

![Koordinator](https://img.shields.io/badge/Koordinator-0.0.14-blue.svg)
 ![Kotlin](https://img.shields.io/badge/Language-Kotlin-orange.svg)![minSdk](https://img.shields.io/badge/Android-minSdk%2014-green.svg)

Koordinator is a simple pattern for Android applications to separate specific navigation implementation from view controllers.

## Installation

```groovy
allprojects {
    repositories {
        jcenter()
    }
}

dependencies {
    implementation "at.florianschuster.koordinator:koordinator-core:$version"
    
    /**
     * Lifecycle Extensions for Koordinator. See: Koordinator-Android
     */
    implementation "at.florianschuster.koordinator:koordinator-android:$version"
    
    /**
     * Lifecycle Extensions for Koordinator. See: Koordinator-Android-Koin
     */
    implementation "at.florianschuster.koordinator:koordinator-android-koin:$version"
}
```

## What should I know before I try this?

* Kotlin

## Tell me more

### General

#### CoordinatorRoute
A **CoordinatorRoute** expresses a way that a **Coordinator** can follow for navigation.

It is not up to the **CoordinatorRoute** to decide where or how to navigate. Do not couple view flows to a **CoordinatorRoute**, rather try to define a coordinated action as **CoordinatorRoute**.

For example use

``` kotlin
sealed class MoviesRoute : CoordinatorRoute {
    data class OnMovieSelected(val movieId: Long) : MoviesRoute() // do
}
```

 instead of
 
 ``` kotlin
 sealed class MoviesRoute : CoordinatorRoute {
    data class OpenMovieDetailFragment(val movieId: Long) : MoviesRoute() // don't
}
```

Each screen should have an object that implements **CoordinatorRoute** to define the directions of its navigation. This could be an enum or a sealed class.

#### Router

The **Router** can be used in a view independent class to follow a specific **CoordinatorRoute**. A flow specific **Coordinator** then uses the **Router** to determine where to navigate.

``` kotlin
class MoviesViewModel: ViewModel() {
    fun doSomething() {
        Router follow MoviesRoute.OnMovieSelected(420)
    }
}
```

#### Coordinator

A **Coordinator** handles navigation or view flow for one or more view controllers (e.g. `Fragment`, `Activity`, `ViewGroup`, `View`). Its purpose is to isolate navigation logic.

``` kotlin
class MoviesCoordinator : Coordinator<MoviesRoute, NavController>() {
    override fun navigate(route: MoviesRoute, handler: NavController) {
        when (route) {
            is MoviesRoute.OnMovieSelected -> Directions.actionOverviewToDetail(route.id)
            is MoviesRoute.SearchIsRequired -> Directions.actionOverviewToSearch()
        }.also(handler::navigate)
    }
}
```

A **Coordinator** needs a specific implementation of **CoordinatorRoute** that it should handle and a **NavigationHandler** that then actually navigates. The **NavigationHandler** is provides by the view and could for example be a `FragmentManager`, a `NavigationController` or a `Context`.

``` kotlin
class MoviesFragment: Fragment() {
    val coordinator = MoviesCoordinator()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coordinator.provideNavigationHandler(this.findNavController())
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        coordinator.onCleared()
    }
}
```

Make sure to call `fun onCleared()` of **Coordinator** after you are done with it. This clears the internal `CompositeDisposable` and the reference to the **NavigationHandler**.


### Koordinator-Android

A **LifecycleCoordinator** is a **Coordinator** that implements `LifecycleObserver` and calls its `fun onCleared()` when the observed `Lifecycle` calls `Lifecycle.Event.ON_DESTROY`.

Use the `LifecycleOwner.lifecycleCoordinator(...)` extension function to bind a **LifecycleCoordinator** to a `lifecycle` of a `LifecycleOwner`.

``` kotlin
class LifecycleMoviesFragment: Fragment() {
    val coordinator by lifecycleCoordinator { MoviesCoordinator() }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coordinator.provideNavigationHandler(this.findNavController())
    }
}
```

### Koordinator-Android-Koin

[Koin](https://github.com/InsertKoinIO/koin) is a lightweight dependency injection framework for Kotlin.

The `androidkoin` module contains simple extension functions to inject a **LifecycleCoordinator**. They are just renamed Koin extension functions that can be used for more clarity when developing with the framework.

### Examples

* [Watchables](https://github.com/floschu/Watchables): A Movie and TV Show Watchlist Application. It uses [Koin](https://github.com/InsertKoinIO/koin) as DI Framework and [Reaktor](https://github.com/floschu/Reaktor) as architectural MV* pattern.

## Author

Visit my [Website](https://florianschuster.at/).

## License

```
Copyright 2019 Florian Schuster.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
