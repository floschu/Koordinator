package at.florianschuster.koordinator

import org.junit.Test

class CoordinatorTest {

    @Test
    fun testCoordinatorOnlyNavigatesWhenHandlerAttached() {
        val followedRoutes: ArrayList<CoordinatorRoute> = arrayListOf()
        val coordinator = object : Coordinator<TestCoordinatorRoute, Any>() {
            override fun navigate(route: TestCoordinatorRoute, handler: Any) {
                followedRoutes.add(route)
            }
        }

        Router follow TestCoordinatorRoute.FIRST
        coordinator.provideNavigationHandler(Unit)
        Router follow TestCoordinatorRoute.FIRST
        Router follow TestCoordinatorRoute.SECOND
        Router follow TestCoordinatorRoute.THIRD

        assert(followedRoutes.count() == 3)
        assert(
            followedRoutes == listOf(
                TestCoordinatorRoute.FIRST,
                TestCoordinatorRoute.SECOND,
                TestCoordinatorRoute.THIRD
            )
        )
    }

    @Test
    fun testCoordinatorOnlyNavigatesOnCorrectSubRoute() {
        val followedRoutes: ArrayList<CoordinatorRoute> = arrayListOf()
        val coordinator = object : Coordinator<TestCoordinatorRoute, Any>() {
            override fun navigate(route: TestCoordinatorRoute, handler: Any) {
                followedRoutes.add(route)
            }
        }

        coordinator.provideNavigationHandler(Unit)
        Router follow TestCoordinatorRoute.FIRST
        Router follow NonTestCoordinatorRoute
        Router follow TestCoordinatorRoute.SECOND
        Router follow TestCoordinatorRoute.THIRD

        assert(followedRoutes.count() == 3)
        assert(
            followedRoutes == listOf(
                TestCoordinatorRoute.FIRST,
                TestCoordinatorRoute.SECOND,
                TestCoordinatorRoute.THIRD
            )
        )
    }
}

private enum class TestCoordinatorRoute : CoordinatorRoute {
    FIRST, SECOND, THIRD
}

private object NonTestCoordinatorRoute : CoordinatorRoute