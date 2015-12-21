package recoveries

case class ClientException(msg: String) extends Exception(msg)
