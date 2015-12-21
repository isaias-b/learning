package recoveries

import scala.util._
import org.scalatest.{ Matchers, FunSpec }
import org.scalatest.junit.JUnitRunner
import org.junit.runner._

@RunWith(classOf[JUnitRunner])
class RecoverSpec extends FunSpec with Matchers {

  describe("toSafeInt returns a parser from string to to int, which") {
    it("should turn invalid integer strings into instances of Failure(ClientException)") {
      Recover.safeToInt("asdf") should be(Failure(new ClientException(""""$s" is not a valid <get type info for T>""")))
    }
    it("should turn valid integer strings xx into instances of Success[Int](xx)") {
      Recover.safeToInt("123") should be(Success[Int](123))
    }
  }
  describe("toSafeResult") {
    import play.api.mvc._
    import play.api.mvc.Results._
    it("should return any given xx result when given a Success(xx)") {
      Recover.toSafeResult(Try(Ok)) should be(Ok)
    }
    it("should return a BadRequest(msg) result for any given ClientException(msg)") {
      val msg = "the client formed a bad request"
      val t: Try[Result] = Failure(new ClientException(msg))
      Recover.toSafeResult(t).toString() shouldBe (BadRequest(msg).toString)
    }
    it("should return a NotImplemented result for org.apache.commons.lang3.NotImplementedException") {
      import org.apache.commons.lang3.NotImplementedException
      val t: Try[Result] = Failure(new NotImplementedException(""))
      Recover.toSafeResult(t) shouldBe (NotImplemented)
    }
    it("should return a NotImplemented result for scala.NotImplementedError") {
      def test = ???
      val t: Try[Result] = Try { test }
      Recover.toSafeResult(t) shouldBe (NotImplemented)
    }
    it("should return an InternalServerError on anything else") {
    	val msg = "the server encountered some unhandled exception"
      val t: Try[Result] = Failure(new Throwable(msg))
      Recover.toSafeResult(t).toString shouldBe (InternalServerError(msg).toString())
    }
  }
}