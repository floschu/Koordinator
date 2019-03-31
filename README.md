# Koordinator
![Koordinator Version](https://img.shields.io/badge/Koordinator-0.0.5-orange.svg) ![minSdk](https://img.shields.io/badge/minSdk-14-green.svg)

Koordinator is a simple pattern for Android applications to separate specific navigation implementation from view controllers.

*TODO*

## Installation

```groovy
allprojects {
    repositories {
        jcenter()
    }
}

dependencies {
    implementation 'at.florianschuster.koordinator:core:0.0.5'
    
    /**
     * Lifecycle Extensions for Koordinator. See: Koordinator-Android
     */
    implementation 'at.florianschuster.koordinator:android:0.0.5'
    
    /**
     * Lifecycle Extensions for Koordinator. See: Koordinator-Android-Koin
     */
    implementation 'at.florianschuster.koordinator:androidkoin:0.0.5'
}
```

## What should I know before I try this?

* Kotlin

## Tell me more

### General

*TODO*

Make sure to call `fun onCleared()` of `Coordinator` after you are done with it. This clears the internal `CompositeDisposable`.

``` kotlin
// per view controller
sealed class MoviesRoute : KoordinatorRoute {
    data class OnMovieSelected(val id: Int) : MoviesRoute()
    object SearchIsRequired : MoviesRoute()
}

// per view controller
class MoviesCoordinator : Coordinator<MoviesRoute, NavController>() {
    override fun navigate(route: MoviesRoute, handler: NavController) {
        when (route) {
            is MoviesRoute.OnMovieSelected -> Directions.actionOverviewToDetail(route.id)
            is MoviesRoute.SearchIsRequired -> Directions.actionOverviewToSearch()
        }.also(handler::navigate)
    }
}

// in view controller (e.g. Fragment)
val coordinator = MoviesCoordinator()
coordinator.provideNavigationHandler(findNavController())

// in view model
Router follow MoviesRoute.OnMovieSelected(420)
```

### Koordinator-Android

Use the `LifecycleOwner.lifecycleCoordinator(...)` extension function to bind a `LifecycleCoordinator` to a `lifecycle` of a `LifecycleOwner`. This then automatically clears the `CompositeDispoable` in `Coordinator`.

### Koordinator-Android-Koin

[Koin](https://github.com/InsertKoinIO/koin) is a lightweight dependency injection framework for Kotlin.

The `androidkoin` module contains simple extension functions to inject a `LifecycleCoordinator`. They are just renamed Koin extension functions that can be used for more clarity when developing with the framework.

### Examples

* [Watchables](https://github.com/floschu/Watchables): A Movie and TV Show Watchlist Application. It uses [Koin](https://github.com/InsertKoinIO/koin) as DI Framework and [Reaktor](https://github.com/floschu/Reaktor) as architectural MV* pattern.
* *TODO*

## Author

Visit my [Website](https://florianschuster.at/).

## AboutLibraries

``` xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="define_koordinator" />
    <string name="library_koordinator_author">Florian Schuster</string>
    <string name="library_koordinator_authorWebsite">https://florianschuster.at</string>
    <string name="library_koordinator_libraryName">Koordinator</string>
    <string name="library_koordinator_libraryDescription">Koordinator is a simple pattern to separate specific navigation implementation from view controllers.</string>
    <string name="library_koordinator_libraryWebsite">https://github.com/floschu/Koordinator</string>
    <string name="library_koordinator_libraryVersion">0.0.5</string>
    <string name="library_koordinator_isOpenSource">true</string>
    <string name="library_koordinator_repositoryLink">https://github.com/floschu/Koordinator</string>
    <string name="library_koordinator_classPath">at.florianschuster.koordinator</string>
    <string name="library_koordinator_licenseId">apache_2_0</string>
</resources>
```

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
