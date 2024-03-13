package lectures.part2oop

object ExampleAuxConstructor extends App {
  val p1 = new Pizza(DefaultCrustSize, DefaultCrustType)
  val p2 = new Pizza(DefaultCrustSize)
  val p3 = new Pizza(DefaultCrustType)
  val p4 = new Pizza
  println(p1)
  println(p2)
  println(p3)
  println(p4)

}
val DefaultCrustSize = 12
val DefaultCrustType = "THIN"

// the primary constructor
class Pizza(var crustSize: Int, var crustType: String) {

  // one-arg auxiliary constructor
  def this(crustSize: Int) = {
    this(crustSize, DefaultCrustType)
  }

  // one-arg auxiliary constructor
  def this(crustType: String) = {
    this(DefaultCrustSize, crustType)
  }

  // zero-arg auxiliary constructor
  def this() = {
    this(DefaultCrustSize, DefaultCrustType)
  }

  override def toString = s"A $crustSize inch pizza with a $crustType crust"

}
