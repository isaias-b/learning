package db

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class UserSpec extends Specification {
  val user = db.User(Some(123), "Hans", "Wurst")

  "ANORM: on table users" should {
    "fetch all shoudl retrieve List(User(123,Hans,Wurst))" in new WithApplication{
      val userOpt = db.User.fetchAll
      userOpt must beEqualTo(List(user))
    }
  }
}
