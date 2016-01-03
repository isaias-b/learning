package commons

/**
 * @author Isaias
 *
 * taken from
 * http://manuel.kiessling.net/2015/01/19/setting-up-a-scala-sbt-multi-project-with-cassandra-connectivity-and-migrations/
 * 
 * Refactored to make it more readable.
 */
import com.chrisomeara.pillar._
import com.datastax.driver.core.Session
import com.chrisomeara.pillar.PrintStreamReporter
import java.util.Date

object Pillar {

  private val registry = Registry(loadedMigrations)
  private val migrator = Migrator(registry, new PrintStreamReporter(Console.out))

  def migrationsDir = "migrations/"
  def migrationNames = JarUtils.getResourceListing(getClass, migrationsDir).toList.filter(_.nonEmpty)
  def migrationPath(name: String) = getClass
    .getClassLoader
    .getResourceAsStream(migrationsDir + name)
  def parseWith(parser: Parser): java.io.InputStream => Migration = stream => try {
    parser.parse(stream)
  } finally {
    stream.close()
  }

  private lazy val loadedMigrations = {
    val parser = Parser()
    migrationNames.map(migrationPath).map(parseWith(parser)).toList
  }

  def initialize(session: Session, keyspace: String, replicationFactor: Int): Unit = {
    migrator.initialize(
      session,
      keyspace,
      new ReplicationOptions(Map("class" -> "SimpleStrategy", "replication_factor" -> replicationFactor)))
  }

  def migrate(session: Session): Unit = {
    try {
      migrator.migrate(session)
    } catch {
      case e: Exception => {
        e.printStackTrace()
      }
    }
  }
}
