package db

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

case class User(id: Option[Int] = None, fname: String, lname: String)

object User {
  val parser: RowParser[User] = for {
    id <- get[Option[Int]]("id")
    fname <- str("fname")
    lname <- str("lname")
  } yield User(id, fname, lname)

  def fetchAll: List[User] = DB.withConnection { implicit c =>
    SQL"""select * from users;"""
    .as(parser.*)
  }

  def fetch(id: Int): Option[User] = DB.withConnection { implicit c =>
    SQL"""select * from users where users.id=$id;"""
    .as(parser.singleOpt)
  }
}