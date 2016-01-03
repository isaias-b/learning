package commons

import com.datastax.driver.core._
import scala.util._

object CassandraSetup extends CassandraSetup(ConnectionUri("cassandra://localhost:9042/mykeyspace")) {
  def run[T]: Block[T] => Try[T] = cluster.run
}

class CassandraSetup(uri: ConnectionUri) {
  implicit class RichCluster(cluster: Cluster) {
    def connectWithKeyspace = {
      val session = cluster.connect
      session.execute(s"USE ${uri.keyspace}")
      session
    }
    def newSession = cluster.connectWithKeyspace
    def run[T]: Block[T] => Try[T] = block => {
      val session = Try { newSession }
      val result = session.map(block)
      session.map(_.close)
      session.flatMap(_ => result)
    }
  }

  def asFunc[T](v: => T): Unit => T = _ => v
  def createCluster = asFunc(uri.createCluster(QueryOptions.DEFAULT_CONSISTENCY_LEVEL))
  def applyMigrations: Cluster => Cluster = cluster => {
    cluster.run { session =>
      Pillar.initialize(session, uri.keyspace, 1)
      Pillar.migrate(session)
    }
    cluster
  }
  def setupCluster = createCluster andThen applyMigrations
  val cluster: Cluster = setupCluster(())

}