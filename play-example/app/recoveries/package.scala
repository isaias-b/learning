import scala.util._
import play.api.mvc._

package object recoveries {
  implicit class RichString(s: String) {
    def safeToInt = Recover.safeToInt(s)
  }
  implicit class RichTryResult(t: Try[Result]) {
    def toSafeResult = Recover.toSafeResult(t)
  }
}