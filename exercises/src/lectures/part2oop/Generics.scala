package lectures.part2oop

import lectures.part2oop.Inheritance.Animal

object Generics extends App {

  class MyList[A] {
    // use the type A in the class definition
    // Can give it whatever name you want
    // Generic class
    // use the type A
    // B is a supertype of type A
    // This means that if I pass something in with type A, it will return something of Type B instead
    def add[B >: A](element: B): MyList[B] = ???
    /*
    A = Cat
    B = Animal
     */

  }
  class MyMap[Key, Value] // this has 2 generic types. AS MANY AS YOU WANT
  val listOfIntegers = new MyList[Int]
  val listOfStrings  = new MyList[String]

  // Generic methods

  object MyList {
    def empty[A]: MyList[A] = ??? // method signture with a generic type
  }
  val emptyListOfIntegers = MyList.empty[Int]

  // variance problem

  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // 1. yes List[Cat] extends List[Animal] = COVARIANCE
  class CovariantList[+A] // +A means it is a COVARIANT list
  val animal: Animal                    = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // animalList.add(new Dog) ??? is this okay? This is a HARD QUESTION to answer - ANSWER, we return a list of animals

  // 2. NO = INVARIANCE
  class InvariantList[A]
  // val invariantAnimalList: InvariantList[Animal] = new InvariantList[Cat] // can't do this. Must be Animal to Animal

  // 3. Hell, no! = CONTRAVARIANCE
  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]

  // bounded types

  // upper bounded type
  class Cage[A <: Animal](
      animal: A
  ) // this says that class Cage can only accept types that are SUBTYPES of type ANIMAL
  // lower bounded type
  // class Cage[A >: Animal] (animal: A)  // this says that class Cage can only accept types that are SUPERTYPES of type ANIMAL

  val cage =
    new Cage(new Dog) // this is an Animal, so this is acceptable for a cage
  class Car
  // val newCage = new Cage(new Car) // this will not run because a new car is NOT an animal

  // expand MyList to be generic
}
