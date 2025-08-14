package lectures.part1basics

object Expressions extends App {
  val x = 1 + 2 // EXPRESSION
  println(x)

  println(2 + 3 * 4)
  // + - * / & | ^ << >> >>> (right shift with zero extension)

  println(1 == x)
  // == != > >= < <=

  println(!(1 == x))
  // ! && ||

  var aVariable = 2
  aVariable += 3 // also works with -= *= /= ... side effects
  println(aVariable)

  // Instructions (DO) vs Expressions (VALUE)

  // IF expression

  val aCondition        = true
  val aConditionedValue = if (aCondition) 5 else 3 // IF EXPRESSION
  println(aConditionedValue)
  println(if (aCondition) 5 else 3)
  println(1 + 3)

  var i = 0
  val aWhile = while (i < 10) {
    println(i)
    i += 1
  }

  // specific to imperative programming.
  // do not write imperative programming in scala
  // NEVER WRITE THIS AGAIN

  // EVERYTHING in scala is an Expression!

  val aWeirdValue = (aVariable = 3) // Unit === void
  println(aWeirdValue)

  // side effects: println(), whiles, reassigning - return Unit

  // Code blocks

  val aCodeBlock = {
    val y = 2
    val z = y + 1

    if (z > 2) "hello" else "goodbye"
    // value of this block is the value of the last expression
    // this is "hello" which is a string. So aCodeBlock is a string
  }
  // val anotherValue = z + 1 // not visible because it is outside of the code block
  // In functional languages, think more of expressions.

  // 1. difference between "hello world" vs println("hello world")?
  // expression vs instruction. // side effect of printing hello world to the console
  // 2 .

  val someValue = {
    2 < 3 // true
  }
  println(someValue)

  val someOtherValue = {
    if (someValue) 239 else 986 // true 239
    42                          // last in code block, so 42 and type Int
  }
  println(someOtherValue)
}
