import scala.util._
import com.datastax.driver.core._

package object commons {
  type Block[T] = Session => T
}