package respositories.impl

import commons._
import model.{ User => Target }
import scala.util._
import scala.collection.JavaConversions._

/**
 * @author Isaias
 */
class PostgresUserRepository extends repositories.UserRepository {

  def toModel(u: db.User): model.User = model.User(u.id.get, u.fname, u.lname)
  def fetch(id: Int): Try[Option[Target]] = Try(db.User.fetch(id)).map(_.map(toModel))
  def fetchAll: Try[List[Target]] = Try(db.User.fetchAll).map(_.map(toModel))

}