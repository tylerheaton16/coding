package lectures.part3fp

import java.util.Random

object Options extends App {

  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int]      = None
  println(myFirstOption)
  println(noOption)

  // handles unsafe APIs

  def unsafeMethod(): String = null // some dummy wrote this method. el oh el
  // val result = Some(unsafeMethod()) // this is wrong. Why Some(null). Wrong. Breaks the point of Options.

  val result = Option(unsafeMethod()) // Option will check null for us
  println(result) // None

  // chained methods
  def backupMethod(): String = "A Valid Result"

  val chainedResult = Option(unsafeMethod()).orElse(
    Option(backupMethod())
  ) // work with unsafe APIs. I.E. chipyard/chisel/diplomacy etc

  // DESIGN unsafe APIs
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A Valid Result")

  val betterChainedResult = betterUnsafeMethod().orElse(betterBackupMethod())
  println(betterChainedResult)

  // functions on Options
  println(myFirstOption.isEmpty) // False
  println(
    myFirstOption.get
  ) // this is unsafe. If option is Null, we will get a null pointer exception.

  // map, flatMap, filter
  println(myFirstOption.map(_ * 2))
  println(myFirstOption.filter(x => x > 10)) // Will turn Some(4) into None
  println(myFirstOption.flatMap(x => Option(x * 10)))

  // for-comprehensions

  /*
  Exercise
   */
  val config: Map[String, String] = Map(
    // fetched from elsewhere - from config file. or some other place.
    // we are not sure there are values in this config. might or might not be here
    "host" -> "176.45.36.1",
    "port" -> "80"
    // values may or may not be here.
  )
  class Connection {
    def connect = "Connected" // connect to some server
  }
  object Connection {
    val random = new Random(System.nanoTime())
    def apply(host: String, port: String): Option[Connection] = {
      // possibility of a connection
      if (random.nextBoolean()) Some(new Connection)
      else None
    }
  }
  // try to establish a connection -- if so, print the connect method
  val host       = config.get("host")
  val port       = config.get("port")
  val connection = host.flatMap(h => port.flatMap(p => Connection.apply(h, p)))

  // below is how I would have done it. Can use flatMap do this kind of pattern matching.
  // Very helpful and unique. Harder to read imo, but also very concise. Understand this
  val connectionUsingMatch = host match {
    case Some(h) => {
      port match {
        case Some(p) => Connection.apply(h, p)
        case None       => None
      }
    }
    case None => None
  }
  val connectionStatus           = connection.map(c => c.connect)
  val connectionStatusUsingMatch = connectionUsingMatch.map(c => c.connect)
  // println(connectionStatus)
  // println(connectionStatusUsingMatch)
  connectionStatus.foreach(println)

  // chained solution
  config
    .get("host")
    .flatMap(host =>
      config
        .get("port")
        .flatMap(port => Connection(host, port))
        .map(connection => connection.connect)
    )
    .foreach(println)

  // for comprehensions
  val forConnectionStatus = for {
    host       <- config.get("host")
    port       <- config.get("port")
    connection <- Connection(host, port)
  } yield connection.connect
  forConnectionStatus.foreach(println)

  // the for way of writing is much more readable. Very preferred.

}
