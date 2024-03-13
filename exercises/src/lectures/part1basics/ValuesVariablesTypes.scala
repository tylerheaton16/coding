package lectures.part1basics

object ValuesVariablesTypes extends App {
  val x: Int = 42
  println(x)
  // VALS ARE IMMUTABLE
  // COMPILER can infer ty pes

  val aString: String = "hello"
  val anotherString   = "goodbye"

  val aBoolean: Boolean = false
  val aChar: Char       = 'a'
  val anInt: Int        = x
  val aShort: Short = 4613 // Short is Int is on 2 bytes, not 4. Half Int size
  val aLong: Long =
    4613436723454346L // Long is Int is on 8 bytes, not 4. Double Int size
  val aFloat: Float   = 2.0f
  val aDouble: Double = 3.14

  val tf = aFloat * aFloat
  val td = aDouble * aDouble

  // variables
  var aVariable: Int = 4
  aVariable = 5 // side effects
  // programs without side effects are easier to understand. Cannot eliminate side effects since
  // the world operates with side effects

}
