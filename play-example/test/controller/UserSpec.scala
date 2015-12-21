package controller

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class UserSpec extends Specification {

  val user = model.User(123, "Hans", "Wurst")

  "Calling GET on the /api/user resource" should {

    "send 400 BAD_REQUEST with an invalid id" in new WithApplication {
      val response = route(FakeRequest(GET, "/api/user/#")).get
      status(response) must beEqualTo(BAD_REQUEST)
    }

    "send 200 OK with a valid id" in new WithApplication {
      val response = route(FakeRequest(GET, "/api/user/123")).get
      status(response) must beEqualTo(OK)
      contentType(response) must beSome.which(_ == "text/plain")
      contentAsString(response) must beEqualTo(Some(user).toString)
    }

    "send 200 OK with a valid but non existent id" in new WithApplication {
      val response = route(FakeRequest(GET, "/api/user/0")).get
      status(response) must beEqualTo(OK)
      contentType(response) must beSome.which(_ == "text/plain")
      contentAsString(response) must beEqualTo("None")
    }
  }

  "Calling GET on the /api/user/all resource" should {

    "send 200 OK" in new WithApplication {
      val response = route(FakeRequest(GET, "/api/user/all")).get
      status(response) must beEqualTo(OK)
      contentType(response) must beSome.which(_ == "text/plain")
      contentAsString(response) must beEqualTo(List(user).toString)
    }
  }

}
