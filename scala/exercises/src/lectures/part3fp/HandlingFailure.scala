package lectures.part3fp

import scala.util.Success
import scala.util.Failure
import scala.util.Try
import java.util.Random
import lectures.part3fp.Options.connectionStatus

object HandlingFailure extends App {

  // create success and failure
  val aSuccess = Success(3)
  val aFailure = Failure(new RuntimeException("SUPER FAILURE"))
  println(aSuccess)
  println(aFailure)

  def unsafeMethod(): String = throw new RuntimeException(
    "NO STRING FOR YOU DUMB ASS"
  )

  // Try objects via the apply method
  val potentialFailure = Try(unsafeMethod())
  println(potentialFailure)

  // syntatic sugar
  val anotherPotentialFailure = Try {
    // code that might throw a failure
  }

  // utilities
  println(potentialFailure.isSuccess)

  // orElse
  def backupMethod(): String = "A valid result for not a dumb ass"
  val fallbackTry            = Try(unsafeMethod()).orElse(Try(backupMethod()))
  println(fallbackTry)

  // IF you design the API
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException)
  def betterBackupMethod(): Try[String] = Success("A valid result")
  val betterFallback = betterUnsafeMethod() orElse betterBackupMethod()
  println(betterFallback)

  // map, flatMap, filter
  println(aSuccess.map(_ * 2))
  println(aSuccess.flatMap(x => Success(x * 2)))
  println(aSuccess.filter(_ > 10))

  // means we can use for comprehensions

  /*
  Exercise
   */

  val host                     = "localhost"
  val port                     = "8080"
  def renderHTML(page: String) = println(page)

  class Connection {
    def get(url: String): String = {
      val random = new Random(System.nanoTime())
      if (random.nextBoolean()) "<html>...</html>"
      else throw new RuntimeException("Connection interrupted")
    }
    // wrapping this in a Try contains the exceptions that could be thrown
    def getSafe(url: String): Try[String] = Try(get(url))
  }

  object HttpService {
    val random = new Random(System.nanoTime())
    def getConnection(host: String, port: String): Connection = {
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException("Someone else took the port. Get wreckd")
    }
    // wrapping this in a Try contains the exceptions that could be thrown
    def getSafeConnection(host: String, port: String): Try[Connection] = Try(
      getConnection(host, port)
    )

  }

  // an attempt to do this
  // val attempt = HttpService.getConnection(hostName, port)
  // val myConnection = Try(attempt.get("gogo.com")).flatMap(x => renderHTML(x))
  // println(myConnection)

  // if you get the html page from the connection, print it to the console i.e. call renderHTML
  val possibleConnection = HttpService.getSafeConnection(host, port)
  val possibleHTML =
    possibleConnection.flatMap(connection => connection.getSafe("/home"))
  possibleHTML.foreach(x => renderHTML(x))
  HttpService
    .getSafeConnection(host, port)
    .flatMap(connection => connection.getSafe("/home"))
    .foreach(renderHTML)

  for {
    connection <- HttpService.getSafeConnection(host, port)
    html       <- connection.getSafe("/home")
  } yield renderHTML(html)
}
