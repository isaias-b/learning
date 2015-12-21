package model

import javax.inject.Inject
import org.scalatest.{ Matchers, FunSpec }
import org.scalatest.junit.JUnitRunner
import org.junit.runner._
import com.datastax.driver.core._

@RunWith(classOf[JUnitRunner])
class UserSpec @Inject() (repo: repositories.UserRepository) extends FunSpec with Matchers {

  val user = model.User(123, "Hans", "Wurst")

  describe("on table users") {
    it("fetch(123) should retrieve Some(User(123,Hans,Wurst))") {
      val userOpt = repo.fetch(123).get
      userOpt should be(Some(user))
    }
    it("fetch(0) should retrieve None") {
      val userOpt = repo.fetch(0).get
      userOpt should be(None)
    }
    it("fetch all shoudl retrieve List(User(123,Hans,Wurst))") {
      val userOpt = repo.fetchAll.get
      userOpt should be(List(user))
    }
  }
}
