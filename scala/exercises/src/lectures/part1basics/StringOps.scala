package lectures.part1basics

object StringOps extends App {

  val str: String = "Hello, I am learning scala"

  println(str.charAt(2))
  println(str.substring(7, 11))
  println(str.split(" ").toList)
  println(str.startsWith("Hello"))
  println(str.replace(" ", "-"))
  println(str.toLowerCase())
  println(str.length)

  // specific to scala
  val aNumberString = "45"
  val aNumber       = aNumberString.toInt
  println('a' +: aNumberString)
  println('a' +: aNumberString :+ 'z')
  println(str.reverse)
  println(str.take(2))

  // Scala-specific: String interpolators

  // S-interpolators
  val name = "David"
  val age  = 12
  // requires s or it will be taken literally
  val greeting = s"Hello, my name is $name and I am $age years old"
  val anotherGreeting =
    s"Hello, my name is $name and I will be turning ${age + 1} years old"
  println(anotherGreeting)

  // F-interpolators

  val speed = 1.2f // float
  val myth  = f"$name can eat $speed%2.2f burgers per minute"
  println(myth)

  // raw-interpolator
  println(raw"This is a \n newline")
  val escaped = "This is a \n newline"
  println(
    raw"$escaped"
  ) // Raw ignores escaped characters in the raw string. In the variable, it is okay

}
