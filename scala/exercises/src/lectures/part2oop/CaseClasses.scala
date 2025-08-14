package lectures.part2oop

object CaseClasses extends App {
  /* equals, hashCode, to String */

  case class Person(name: String, age: Int)
  // 1. Class parameters are now fields
  val jim = new Person("jim", 34)
  println(
    jim.name
  ) // valid because it is a case class (or put val in front of name in parameters inputs)

  // 2. sensible toString
  println(jim.toString) // case classes give us simple string representations
  println(
    jim
  ) // prints out as Person(jim, "34"), not lectures.part2oop.CaseClasses$Person.....@230o953

  // 3. equals and hashCode implemented out of the box
  val jim2 = new Person("jim", 34)
  println(jim == jim2) // true in case class. false if not

  // 4. Case Classes have handy copy methods
  val jim3 = jim.copy(age = 45)
  println(jim3)

  // 5. Case Classes have companion objects
  val thePerson = Person
  val mary      = Person("Mary", 23)

  // 6. Case Classes are serializable
  // Akka

  // 7. Case Classes have extractor patterns = CCs can be used in PATTERN MATCHING

  case object UnitedKingdom {
    def name: String = "The UK of GB and NI"
  }

  /*
 Expand MyList - use case clsses and case objects
   */

}
