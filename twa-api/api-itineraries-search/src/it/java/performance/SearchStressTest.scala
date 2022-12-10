package performance

import com.intuit.karate.gatling.PreDef._
import io.gatling.core.Predef._
import scala.concurrent.duration._

class SearchStressTest extends Simulation {

  def urlPattern = "GET /api/flights/itineraries-search/itineraries"


  object PerformanceConstants {

    def secondMillis = 2000

    val (mean, percentile_75, percentile_95) = {
      (secondMillis, (2 * secondMillis).toInt, (2.5 * secondMillis).toInt)
    }

  }

  val protocol = karateProtocol(
    "/api/flights/itineraries-search/itineraries" -> Nil
  )

  val createScenario = scenario("Search").exec(karateFeature("classpath:search/search.feature"))

  setUp(
    createScenario.inject(
      rampUsers(5) during (10 seconds)
    ).protocols(protocol)
  ).assertions(
    details(urlPattern).responseTime.mean.lte(PerformanceConstants.mean),
    details(urlPattern).responseTime.percentile2.lte(PerformanceConstants.percentile_75),
    details(urlPattern).responseTime.percentile3.lte(PerformanceConstants.percentile_95),
    details(urlPattern).failedRequests.count.lte(10)
  )

}