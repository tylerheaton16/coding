package lectures.part2oop

import lectures.playground.{Cinderella => Princess}
import lectures.playground.PrinceCharming
import java.util.{Date => utilDate}
import java.sql.Date

object PackagingAndImports extends App {

  // package members are accesible by their simple name
  val writer = new Writer("Daniel", "RockTheJVM", 2018)

  // import the package if you are not working in it

  //val princess = new lectures.playground.Cinderella // fully qualified class name
  //val princess = new Cinderella
  val princess = new Princess

  // packages are in hierarchy
  // matching folder structure

  // package object
  sayHello
  println(SPEED_OF_LIGHT)

  // imports
  val prince = new PrinceCharming

  val date = new utilDate // will assume java util Date
  val dqlDate = new java.sql.Date(2018, 5, 4) // use fully qualified name to solve this if you need

  // default imports
  // java.lang - String, Object, Exception, ....
  // scala - Int, Nothing, Function, ...
  // scala.Predef - println, ???, ...
}
