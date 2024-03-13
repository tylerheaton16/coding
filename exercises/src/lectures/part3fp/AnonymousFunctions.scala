package lectures.part3fp

object AnonymousFunctions extends App {

  // val doubler = new Function1[Int, Int] {
  //  override def apply(x: Int): Int = x * 2
  // }

  // anonymous function (LAMBDA)
  // if the compiler already has the type, then you can assume the expression will match its type
  // you can then just do x => x * 2
  // val doubler = (x: Int)  => x * 2 // syntatic sugar for above.
  val doubler: Int => Int = x => x * 2 // syntatic sugar for above.

  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b

  // what happens if you don't have any parameters
  val justDoSomething: () => Int = () => 3 // no input params so we do ()

  println(justDoSomething) // function itself
  println(
    justDoSomething()
  ) // acutal call // lambdas MUST be called with the parentheses

  // curly braces with lambdas
  val stringToInt = { (str: String) =>
    str.toInt
  }

  // MORE syntatic sugar

  val niceIncrementer: Int => Int = _ + 1 // equivalent to x => x + 1
  val niceAdder: (Int, Int) => Int = _ + _ // equivalent to (a,b) => a + b
  // each underscore is a different parameter
  // MUST add the type to the val or the compiler will not be able to complete using _

  /*
    1. MyList: replace all FunctionX calls with lambdas
    2. Rewrite the "special" adder as an anonymous function
  */
  val superAdd: Int => Int => Int = x => y => x + y // curried
  println(superAdd(3)(4))
}
