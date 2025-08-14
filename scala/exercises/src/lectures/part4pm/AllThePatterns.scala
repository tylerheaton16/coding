package lectures.part4pm

import exercises.{MyList, Cons}
import exercises.Empty

object AllThePatterns extends App {

  // 1 - constants
  val x: Any = "Scala"
  val constants = x match {
    case 1              => "a number"
    case "Scala"        => "THE Scala"
    case true           => "The Truth"
    case AllThePatterns => "A singleton object"
  }

  // 2 - match anything
  // 2.1 wildcard
  val matchAnything = x match {
    case _ => // wild card babyyyy
  }

  // 2.2 Variable
  val matchAVariable = x match {
    case something => s"I've found $something"
  }

  // 3 - tuples
  val aTuple = (1, 2)
  val matchATuple = aTuple match {
    case (1, 1)         =>
    case (something, 2) => s"I've found $something"
  }

  val nestedTuple = (1, (2, 3))
  val matchANestedTuple = nestedTuple match {
    case (_, (2, v)) => s"I've found $v"
  }
  // PMs (pattern matches) can be nested

  // 4 - case classes - constructor pattern

  // PMs can be nested with case classes as well
  val aList: MyList[Int] = Cons(1, Cons(2, Empty))
  val matchAList = aList match {
    case Empty                              =>
    case Cons(head, Cons(subhead, subtail)) =>
    // case Cons(head, tail) => // head will be 1, tail will be Cons(2, Empty)
  }

  // 5 - list patterns
  val aStandardList = List(1, 2, 3, 42)
  val standardListmatching = aStandardList match {
    case List(1, _, _, _) => // Example of an extractor - advanced
    case List(1, _*)      => // list of arbitrary length
    case 1 :: List() => // infix pattern. Lots of magic work here. Super super helpful in chisel
    case List(1, 2, 3) :+ 42 => // infix pattern
  }

  // 6 - typer specifiers
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case list: List[Int] => // explicit type specifier
    case _               =>
  }

  // 7 - name binding
  val nameBindingMatch = aList match {
    case nonEmptyList @ Cons(
          _,
          _
        ) => // name binding => use the name in the return expression
    case Cons(1, rest @ Cons(2, _)) => // rest @ subpattern will still work
  }

  // 8 - multi-patterns
  val multipattern = aList match {
    case Empty | Cons(0, _) => // compound pattern. One or the other
  }

  // 9 - if guards
  val secondElementSpecial = aList match {
    case Cons(_, Cons(specialElement, _))
        if specialElement % 2 == 0 => // If guard will filter out the pattern by the predicate at the end
  }

  // All.

  /*
  Question
   */

  val numbers = List(1, 2, 3)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => "a List of strings"
    case listOfNumbers: List[Int]     => "a list of numbers"
    case _ => ""
  }
  println(numbersMatch) // unfortunately, not a listOfNumbers.
  // We get a listOfStrings
  // JVM trick question

  // Our type parameters are wiped away because JVM didn't add generics until JVM 5
  // type erasure problems
}
