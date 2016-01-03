package repositories

import scala.util._
import com.google.inject.ImplementedBy

/**
 * @author Isaias
 */
@ImplementedBy(classOf[cql.CassandraUserRepository])
trait UserRepository {
  def fetch(id: Int): Try[Option[model.User]]
  def fetchAll: Try[List[model.User]]
}