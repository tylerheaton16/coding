package lectures.part4pm

import scala.util.Random

object PatternMatching extends App {

  // switch on steroids
  val random = new Random
  val x      = random.nextInt(10)

  // much more powerful than switch statements you have seen in other languages
  val description = x match {
    case 1 => "the ONE"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => "sucks to suck"
  }
  println(x)
  println(description)

  // 1. Decompose values
  // case classes have the ability to be deconstructed in pattern matching

  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    case Person(n, a) if a < 21 => s"Hi, my name is $n, and I am $a years old"
    case Person(n, a)           => s"Hi, my name is $n, and I am $a years old"
    case _ => "Again, I don't know who I am. Sucks to suck."
  }
  println(greeting)
  /*
  1. Cases are computed in order
  What if no cases are matched?
    - we get a scala match error. Cover your ass with wild cards
  2. What is the type?
    - it will try to unify the types. If we return an Int in a case and a String in another, it will return the most similar type of them all: Any
    - this is very important when trying to return greeting as a specific type.
  3. PM works really well with case classes. This is because case classes get pattern matching out of the box
   */

  // Pattern matching on sealed hierarchies

  // sealed classes help you cover your ass in matching
  // the compiler will tell you that you are not being exhaustive if you use a sealed class
  sealed class Animal
  case class Dog(bread: String)       extends Animal
  case class Parrot(greeting: String) extends Animal

  val animal: Animal = Dog("Terra Nova")
  animal match {
    case Dog(someBreed) => println(s"Matched a dog of the $someBreed breed")
  }

  // people who see pattern matching try to match everything
  val isEven = x match {
    case n if n % 2 == 0 => true
    case _ => false
  }
  // this is complete overkill when pattern matching.
  // use common sense. You can just do x % 2 == 0
  val isEvenCond   = if (x % 2 == 0) true else false // Why? Literally no point
  val isEvenNormal = x % 2 == 0 // literally this.

  /*
  Exercise
  simple function uses PM
  takes an Expr => human readable form

  Sum(Number(2), Number(3)) => 2 + 3
  SumSum((Number(2), Number(3), Number(4))) => 2 + 3 + 4
  Product(Sum(Number(2), Number(1)), Number(3)) = (2+1) * 3 // parentheses are necessary
  Sum(Prod(Number(2), Number(1)), Number(3)) => 2 * 1 + 3 // parentheses are not necessary
   */

  trait Expr
  case class Number(n: Int)           extends Expr
  case class Sum(e1: Expr, e2: Expr)  extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(e: Expr): String = e match {
    case Number(n)   => s"$n"
    case Sum(e1, e2) => show(e1) + " + " + show(e2)
    case Prod(e1, e2) => {
      def maybeShowParentheses(exp: Expr) = exp match {
        case Prod(_, _) => show(exp)
        case Number(_)  => show(exp)
        case _          => "(" + show(exp) + ")"
      }
      maybeShowParentheses(e1) + " * " + maybeShowParentheses(e2)
    }
  }
  println(show(Sum(Number(2), Number(3))))
  println(show(Sum(Sum(Number(2), Number(3)), Number(4))))
  println(show(Prod(Sum(Number(2), Number(1)), Number(3))))
  println(show(Sum(Prod(Number(2), Number(1)), Number(3))))
  println(show(Number(2)))
}
