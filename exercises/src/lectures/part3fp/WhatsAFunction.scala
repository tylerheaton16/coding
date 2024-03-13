package lectures.part3fp

object WhatsAFunction extends App {

  // use functions as first class elements
  // work with functions like we work with plain values (val)
  // problem: we come from an oop world. Everything is an instance of some class
  // had to use classes and instances of those classes

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(2)) // we can call doubler(2) as if it was a function

  // function types = Function1, Function2, Function3, ... Function22
  // function types = Function1[A, B]

  val stringToIntConverter = new Function1[String, Int] {
    override def apply(string: String): Int = string.toInt
  }

  // val adder: Function2[Int, Int, Int] = new Function2[Int, Int, Int] {
  val adder: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }

  // Function Types Function[A, B, C] === (A,B) => R
  // ALL SCALA FUNCTIONS ARE OBJECTS
  println(stringToIntConverter("3") + 4)

  /*
    1. a function which takes 2 strings and concatentates them
    2. transform the MyPredicate and MyTransformer into function types
    3. define a function which takes an argument an int and returns another function which takes an int and returns an int
      - what's the type of this function
      - how to do it
   */

  val stringConcatentator: ((String, String) => String) =
    new Function2[String, String, String] {
      override def apply(a: String, b: String): String = a + b
    }
 

  // Function1[Int, Function1[Int, Int]]
  val superAdder: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
    override def apply(x: Int): Function1[Int, Int] = new Function1[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }

  println(stringConcatentator("me", "too"))
  val adder3 = superAdder(3)
  println(adder3)
  println(adder3(4))
  println(superAdder(3)(4))  // Curried Function
}

//what an OOP language would let you do. So, how do we make this functional?
//trait Action[A, B] {
//  def execute(element: A): B = ???
//}
trait MyFunction[A, B] {
  def apply(element: A): B = ???
}
