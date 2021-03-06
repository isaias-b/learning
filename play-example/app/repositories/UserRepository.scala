package repositories

import scala.util._
import com.google.inject.ImplementedBy
import respositories.impl.{CassandraUserRepository => UserRepositoryImpl}

/**
 * @author Isaias
 */
@ImplementedBy(classOf[UserRepositoryImpl])
trait UserRepository {
  def fetch(id: Int): Try[Option[model.User]]
  def fetchAll: Try[List[model.User]]
}