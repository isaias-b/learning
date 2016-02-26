
package main

import jssc.SerialPortList

object Main {
  def main(args: Array[String]) = {
    println("List of available ports:")
    println(SerialPortList.getPortNames().mkString("\n"))
  }
}