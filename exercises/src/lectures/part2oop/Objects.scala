package lectures.part2oop

object Objects extends App {
  // SCALA DOES NOT HAVE CLASS-LEVEL FUNCITONALITY (NO STATIC) - WE HAVE OBJECTS WOOO

  object Person { // type = its only instance
    // "static"/"class" - level functionality
    val N_EYES          = 2 // "class" level functionality
    def canFly: Boolean = false

    // factory method
    def apply(mother: Person, father: Person): Person = new Person("Bobbie")
  }
  println(Person.N_EYES)
  println(Person.canFly)

  class Person(val name: String) {
    // instance-level functionality

  }
  // COMPANIONS because they have the same name and are in the same scope

  // Scala object = SINGLETON INSTANCE
  val mary = new Person("Mary")
  val john = new Person("John")
  println(mary == john)

  val person1 = Person
  val person2 = Person
  println(person1 == person2)

  val bobbie      = Person.apply(mary, john)
  val bobbieApply = Person(mary, john)

  // Scala Applications = Scala object with
  // def main(args: Array[String]): Unit
}
