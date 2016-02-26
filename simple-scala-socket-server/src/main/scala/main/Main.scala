
package main

import scala.util._
import java.net._
import java.io._

object Main {
  def main(args: Array[String]) = {
    val port = Try(args(0).toInt).getOrElse(2023)
    println(s"Try to start echo server on port $port")
    val ss = new ServerSocket(port)
    val cs = ss.accept
    println(s"Client connected on port $port")
    val is = new BufferedInputStream(cs.getInputStream)
    val os = new PrintStream(new BufferedOutputStream(cs.getOutputStream))
    os.println("Hi there.")
    os.flush()
    println(s"Client($port): Greeted")
    
    while(is.available() < 1) Thread.sleep(100)
    val buf = new Array[Byte] (is.available())
    is.read(buf)
    val in = new String(buf)
    println(in)
    
    os.println(in)
    os.flush()
  }
}