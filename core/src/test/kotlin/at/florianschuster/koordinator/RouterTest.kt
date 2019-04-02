package at.florianschuster.koordinator

import org.junit.Test

class RouterTest {

    @Test
    fun testRouterFollowsRoute() {
        val testObserver = Router.routes.test()

        Router follow RouterTestRoute.FIRST
        Router follow RouterTestRoute.THIRD
        Router follow RouterTestRoute.SECOND

        testObserver.values().let { results ->
            assert(results.size == 3)
            assert(results == listOf(RouterTestRoute.FIRST, RouterTestRoute.THIRD, RouterTestRoute.SECOND))
        }
    }
}

private enum class RouterTestRoute : CoordinatorRoute {
    FIRST, SECOND, THIRD
}