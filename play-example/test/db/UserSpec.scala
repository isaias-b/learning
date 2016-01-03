package db

import play.api.test._
import play.api.test.Helpers._

import org.scalatestplus.play._
import org.scalatest.Matchers
import org.scalatest.junit.JUnitRunner
import org.junit.runner._

@RunWith(classOf[JUnitRunner])
class UserSpec extends PlaySpec with OneBrowserPerSuite with OneServerPerSuite with HtmlUnitFactory {
  val user = db.User(Some(123), "Hans", "Wurst")

  "ANORM: on table users" should {
    "fetch all shoudl retrieve List(User(123,Hans,Wurst))" in {
      val userOpt = db.User.fetchAll
      userOpt must be(List(user))
    }
  }
}
