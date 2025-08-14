package lectures.part2oop

object AbstractDataTypes extends App {

  // abstract
  abstract class Animal { // abstract classes cannot be instantiated
    val creatureType: String = "wild" // this value is abstract
    def eat: Unit // this method is also abstract
  }
  // val animal = new Animal // cannot be instantiated because it is abstract

  class Dog extends Animal {
    override val creatureType: String =
      "Canine" // don't have to use override since not implemented
    def eat: Unit = println("crunch crunch")
  }

  // traits
  trait Carnivore {
    def eat(animal: Animal): Unit
    val preferredMeal: String = "fresh meat"
  }

  trait ColdBlooded
  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "croc"
    def eat: Unit                     = println("nomnomnom")
    def eat(animal: Animal): Unit = println(
      s"I'm a croc and I'm eating ${animal.creatureType}"
    )
  }

  val dog  = new Dog
  val croc = new Crocodile
  croc.eat(dog)

  // traits vs abstract classes
  // 1. traits do not have constructor parameters
  // 2. multiple traits may be inherited by the same class
  // 3. traits = behavior, abstract class = "thing"
}
