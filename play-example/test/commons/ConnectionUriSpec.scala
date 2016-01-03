package commons

import org.scalatest.{ Matchers, FunSpec }
import com.datastax.driver.core._


import org.scalatest.junit.JUnitRunner
import org.junit.runner._

@RunWith(classOf[JUnitRunner])
class ConnectionUriSpec extends FunSpec with Matchers {

  describe("A Cassandra connection URI object") {
    it("should parse a URI with a single host") {
      val cut = ConnectionUri("cassandra://localhost:9042/test")
      cut.host should be("localhost")
      cut.hosts should be(Seq("localhost"))
      cut.port should be(9042)
      cut.keyspace should be("test")
    }
    it("should parse a URI with additional hosts") {
      val cut = ConnectionUri(
        "cassandra://localhost:9042/test" +
          "?host=otherhost.example.net" +
          "&host=yet.anotherhost.example.com")
      cut.hosts should contain allOf ("localhost", "otherhost.example.net", "yet.anotherhost.example.com")
      cut.port should be(9042)
      cut.keyspace should be("test")
    }
  }

}
