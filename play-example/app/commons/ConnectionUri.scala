package commons

import java.net.URI
import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.Cluster
import com.datastax.driver.core.QueryOptions

case class ConnectionUri(connectionString: String) {

  private val uri = new URI(connectionString)

  private val additionalHosts = Option(uri.getQuery) match {
    case Some(query) => query
      .split('&')
      .map(_.split('='))
      .filter(param => param(0) == "host")
      .map(param => param(1))
      .toSeq
    case None => Seq.empty
  }

  val host = uri.getHost
  val hosts = Seq(uri.getHost) ++ additionalHosts
  val port = uri.getPort
  val keyspace = uri.getPath.substring(1)

  def createCluster(defaultConsistencyLevel: ConsistencyLevel): Cluster = {
    new Cluster.Builder().
      addContactPoints(hosts.toArray: _*).
      withPort(port).
      withQueryOptions(
        new QueryOptions().
          setConsistencyLevel(defaultConsistencyLevel)).
        build
  }
}