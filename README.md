# Koordinator
![Koordinator Version](https://img.shields.io/badge/Koordinator-0.0.1-orange.svg) ![minSdk](https://img.shields.io/badge/minSdk-14-green.svg)

Koordinator is a simple pattern to separate specific navigation implementation from view controllers.

*TODO*

## Installation

```groovy
allprojects {
    repositories {
        jcenter()
    }
}

dependencies {
    implementation 'at.florianschuster.koordinator:core:0.0.1'
    
    /**
     * Lifecycle Extensions for Koordinator. See: Koordinator-Android
     */
    implementation 'at.florianschuster.koordinator:android:0.0.1'
}
```

## What should I know before I try this?

* Kotlin
* RxJava2

## Tell me more

### General

*TODO*

``` kotlin
// per view controller
sealed class MoviesRoute : KoordinatorRoute {
    data class OnMovieSelected(val id: Int) : MoviesRoute()
    object SearchIsRequired : MoviesRoute()
}

// per view controller
class MoviesCoordinator(
    router: Router
) : Coordinator<MoviesRoute, NavController>(router) {
    override fun navigate(route: MoviesRoute, handler: NavController) {
        when (route) {
            is MoviesRoute.OnMovieSelected -> Directions.actionOverviewToDetail(route.id)
            is MoviesRoute.SearchIsRequired -> Directions.actionOverviewToSearch()
        }.also(handler::navigate)
    }
}

// singleton
val router = Router()

// in view
val coordinator = MoviesCoordinator(router)
coordinator.provideNavigationHandler(findNavController())

// in view model
router follow MoviesRoute.OnMovieSelected(420)

```

### Koordinator-Android

*TODO*

### Examples

* [Watchables](https://github.com/floschu/Watchables): A Movie and TV Show Watchlist Application. It uses [Koin](https://github.com/InsertKoinIO/koin) as DI Framework and [Reaktor](https://github.com/floschu/Reaktor) as architectural MV* pattern.
* *TODO*

## Author

Visit my [Website](https://florianschuster.at/).

## AboutLibraries

``` xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="define_reaktor"></string>
    <string name="library_reaktor_author">Florian Schuster</string>
    <string name="library_reaktor_authorWebsite">https://florianschuster.at</string>
    <string name="library_reaktor_libraryName">Koordinator</string>
    <string name="library_reaktor_libraryDescription">Koordinator is a simple pattern to separate specific navigation implementation from view controllers.</string>
    <string name="library_reaktor_libraryWebsite">https://github.com/floschu/Koordinator</string>
    <string name="library_reaktor_libraryVersion">0.0.1</string>
    <string name="library_reaktor_isOpenSource">true</string>
    <string name="library_reaktor_repositoryLink">https://github.com/floschu/Koordinator</string>
    <string name="library_reaktor_classPath">at.florianschuster.koordinator</string>
    <string name="library_reaktor_licenseId">apache_2_0</string>
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
