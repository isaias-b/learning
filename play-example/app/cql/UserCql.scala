package cql

import commons._
import model.{ User => Target }

import scala.util._
import scala.collection.JavaConversions._
import com.datastax.driver.core.Row

class UserCql extends repositories.UserRepository {

  def fetch(id: Int): Try[Option[Target]] = CassandraSetup.run(session => {
    val cql = """SELECT * FROM users WHERE user_id = ?;"""
    val prepared = session.prepare(cql)
    val stmt = prepared.bind(id.asInstanceOf[AnyRef])
    val result = session.execute(stmt)
    Try {
      val row = result.one()
      Target(row.getInt(0), row.getString(1), row.getString(2))
    }.toOption
  })
  def fetchAll: Try[List[Target]] = CassandraSetup.run(session => {
    val result = session.execute("""SELECT * FROM users;""")
    val list: List[Row] = result.all().toList
    list.map(r => Target(r.getInt(0), r.getString(1), r.getString(2)))
  })
}