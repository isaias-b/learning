package controller

import play.api.test._
import play.api.test.Helpers._

import org.scalatestplus.play._

import org.scalatest.junit.JUnitRunner
import org.junit.runner._

@RunWith(classOf[JUnitRunner])
class UserSpec extends PlaySpec with OneBrowserPerSuite with OneServerPerSuite with HtmlUnitFactory {

  val user = model.User(123, "Hans", "Wurst")

  "Calling GET on the /api/user resource" should {

    "send 400 BAD_REQUEST with an invalid id" in {
      val response = route(FakeRequest(GET, "/api/user/#")).get
      status(response) must be(BAD_REQUEST)
    }

    "send 200 OK with a valid id" in {
      val response = route(FakeRequest(GET, "/api/user/123")).get
      status(response) must be(OK)
      contentType(response).value must be("text/plain")
      contentAsString(response) must be(Some(user).toString)
    }

    "send 200 OK with a valid but non existent id" in {
      val response = route(FakeRequest(GET, "/api/user/0")).get
      status(response) must be(OK)
      contentType(response).value must be("text/plain")
      contentAsString(response) must be("None")
    }
  }

  "Calling GET on the /api/user/all resource" should {

    "send 200 OK" in {
      val response = route(FakeRequest(GET, "/api/user/all")).get
      status(response) must be(OK)
      contentType(response).value must be("text/plain")
      contentAsString(response) must be(List(user).toString)
    }
  }

}
