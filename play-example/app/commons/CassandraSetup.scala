package commons

import com.datastax.driver.core._
import scala.util._

object CassandraSetup {
  val uri = ConnectionUri("cassandra://localhost:9042/mykeyspace")
  val cluster = createCluster(uri)
  def newSession = cluster.connectWithKeyspace(uri.keyspace)

  def createCluster(
    uri: ConnectionUri,
    defaultConsistencyLevel: ConsistencyLevel = QueryOptions.DEFAULT_CONSISTENCY_LEVEL) = {
    new Cluster.Builder().
      addContactPoints(uri.hosts.toArray: _*).
      withPort(uri.port).
      withQueryOptions(
        new QueryOptions().
          setConsistencyLevel(defaultConsistencyLevel)).
        build
  }

  implicit class RichCluster(cluster: Cluster) {
    def connectWithKeyspace(keyspace: String) = {
      val session = cluster.connect
      session.execute(s"USE ${keyspace}")
      session
    }
  }

  def run[T]: Block[T] => Try[T] = block => {
    val session = Try { newSession }
    val result = session.map(block)
    session.map(_.close)
    session.flatMap(_ => result)
  }
  
}