package lectures.part2oop

object Inheritance extends App {
  // sealed class Animal {}
  sealed class Animal {
    // private def eat = println("nomnom")
    val creatureType = "wild"
    // protected def eat = println("nomnom")
    // final def eat = println("nomnom")
    def eat = println("nomnom")
  }

  // Cat is a subclass of Animal
  // Animal is a superclass of Cat
  // Single class inheritance - can only extend one class at a time
  class Cat extends Animal {
    def crunch = {
      eat
      println("crunch crunch")
    }

  }

  val cat = new Cat
  cat.crunch

  // constructors
  class Person(name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }
  class Adult(name: String, age: Int, idCard: String) extends Person(name)

  // overriding
  class Dog(override val creatureType: String) extends Animal {
    override def eat = {
      super.eat
      println("crunch, crunch")
    }
    // override val creatureType = "domestic"
  }
  // class Dog(override val dogType: String) extends Animal {
  //  override def eat = println("crunch, crunch")
  //  //override val creatureType = dogType
  // }

  val dog = new Dog("K9")
  dog.eat
  println(dog.creatureType)

  // type substituion (broad: polymorphism)
  val unknownAnimal: Animal = new Dog("K9")
  unknownAnimal.eat

  // overRIDING vs overLOADING

  // super - reference a method or field from a parent class

  // preventing overrides
  // 1 - use final
  // 2 - use final on class "final class Animal" to prevent overrides
  // 3 - seal the class software restriction. = You CAN extend classes in THIS FILE, prevent extension in other files
}
