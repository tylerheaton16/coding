package lectures.part4pm

object PatternsEverywhere extends App {
  // big idea #1
  try {
    // code
  } catch {
    case e: RuntimeException       => "runtime"
    case npe: NullPointerException => "npe"
    case _                         => "something else"
  }

  // catches are actually MATCHES
  /*
  try {
    // code
  } catch (e) {
    e match {
      case e: RuntimeException => "runtime"
      case npe: NullPointerException => "npe"
      case _ => "something else"
    }
  }
   */

  // big idea #2
  val list = List(1, 2, 3, 4)
  val evenOnes = for {
    x <- list
    if x % 2 == 0 // doesn't list seem odd after learning about pattern matchign?
  } yield { 10 * x }
  // generators are ALSO based on pattern matching
  val tuples = List((1, 2), (3, 4))
  val filterTuples = for {
    (first, second) <- tuples
  } yield first * second

  // case classes, :: operators, .......

  // big idea #3
  val tuple     = (1, 2, 3)
  val (a, b, c) = tuple // this works from the concept of pattern matching
  // multiple value definitions based on PATTERN MATCHING
  // ALL the power is available for anything
  val head :: tail = list
  println(head)
  print(tail)
  // println(a) // 1
  // println(b) // 2
  // println(c) // 3

  // big idea #4 - NEW
  // partial function based on PATTERN MATCHING
  val mappedList = list.map {
    case v if v % 2 == 0 => v + " is even"
    case 1 => "the one"
    case _ => "something else"
  } // partial function literal

  val mappedList2 = list.map { x =>
    x match {
      case v if v % 2 == 0 => v + " is even"
      case 1 => "the one"
      case _ => "something else"
    }
  }
  println(mappedList)
}
