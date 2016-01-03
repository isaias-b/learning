package controller

import org.scalatestplus.play._

import org.scalatest.junit.JUnitRunner
import org.junit.runner._

@RunWith(classOf[JUnitRunner])
class IntegrationSpec extends PlaySpec with OneBrowserPerSuite with OneServerPerSuite with HtmlUnitFactory {

  "Application" should {

    "work from within a browser" in {

      go to "http://localhost:" + port

      pageSource must include ("Your new application is ready.")
    }
  }
}
