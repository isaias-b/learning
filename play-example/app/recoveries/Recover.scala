package recoveries

import scala.util._
import scala.util._
import play.api.mvc._
import play.api.mvc.Results._

/**
 * We can do exception translation here in order to populate only
 * known and well translated errors to the client. In the end it
 * is possible to filter these known client exceptions on the top
 * level in the controller classes by producing an appropriate
 * Result object.
 */
object Recover {
  type Parser[T] = String => Try[T]
  type ParserRecoverer[T] = Parser[T] => Parser[T]
  def toTry[T](p: (String => T)): Parser[T] = s => Try { p(s) }
  def nfeRecoverer[T]: ParserRecoverer[T] = p => s => p(s).recover {
    case nfe: java.lang.NumberFormatException => throw new ClientException(""""$s" is not a valid <get type info for T>""")
  }
  val safeToInt: Parser[Int] = nfeRecoverer(toTry(_.toInt))

  def toSafeResult(t: Try[Result]): Result = {
    import sun.reflect.generics.reflectiveObjects.{ NotImplementedException => SunNie }
    import org.apache.commons.lang3.{ NotImplementedException => OrgNie }
    t.recover {
      case ClientException(msg)     => BadRequest(msg)
      case onie: OrgNie             => NotImplemented
      case snie: SunNie             => NotImplemented
      case nie: NotImplementedError => NotImplemented
      case e                        => InternalServerError(e.toString)
    }.getOrElse(InternalServerError("Impossible Exception Occoured"))
  }
}