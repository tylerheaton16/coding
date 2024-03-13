package lectures.part2oop

object MethodNotations extends App {

  class Person(val name: String, favoriteMovie: String, val age: Int = 0) {
    def likes(movie: String): Boolean = movie == favoriteMovie
    def +(person: Person): String =
      s"${this.name} is hanging out with ${person.name}"
    def +(person: String = this.name, nickname: String) =
      new Person(this.name + s" (${nickname})", this.favoriteMovie)
    def unary_! : String = s"$name, what the heck?!"
    def unary_+ : Person =
      new Person(this.name, this.favoriteMovie, this.age + 1)
    def isAlive: Boolean = true
    def learns           = s"${this.name} learns Scala"
    def learnsScala      = learns
    def apply(): String  = s"Hi, my name is $name, and I like $favoriteMovie"
    def apply(n: Int): String =
      s"${this.name} watched ${this.favoriteMovie} $n times"
  }
  val mary = new Person("Mary", "Inception")
  println(mary.likes("Inception"))
  println(mary likes "Inception") // equivalent
  // infix notation - operator notation (syntatic sugar) - more resemblic of natural language

  // works with methods with only one parameter

  // "Operators" in Scala
  val tom = new Person("Tom", "Fight Club")
  println(mary + tom) // mary.hangOutWith(tom) // scala has permisive naming
  println(mary.+(tom))
  println(1 + 2)
  println(1.+(2))
  // you can change hangOutWith to +. This Method is a valid name.
  // ALL OPERATORS ARE METHODS
  // Akka actors have ! ?. (not sure what this is yet)

  // prefix notation - another form of syntatic sugar
  val x = -1 // equivalent with 1.unary_-
  val y = 1.unary_-
  // unary_ prefix only work with - + ~ !
  println(!mary)
  println(mary.unary_!) // equivalent

  // postfix notation
  println(mary.isAlive)
  // println(mary isAlive) // Looks like removed in scala 3

  // apply
  println(mary.apply())
  println(
    mary()
  ) // This has the SAME affect. Calling mary as a function works since we have an apply method

  /*
  1. overload the + operator which receives a string
      mary + "the rockstar" => new person "Mary (the rockstart)"

  2. Add an age to the Person class.
      add a unary + operator => new Person class with age + 1
      +mary => mary with the age incrementer
  3. Add a "learns" method in the Person class = "Mary learns Scala"
     Add a learnsScala method, calls learns method with "Scala" as a parameter
     Use it in postfix notation
  4. Overload the apply method
      mary.apply(2) => "Mary watched Inception 2 times"
   */
  // def +(person: String = this.name, nickname: String) = new Person(this.name + s"(${nickname})", this.favoriteMovie)
  val newname = mary.+(nickname = "the rockstar")
  println(newname.apply())
  val hangout = mary.+(tom)
  val newper  = mary.unary_+
  println(newname.name)
  println(hangout)
  println(newper.age)
  println(mary.learns)
  println(mary.apply(2))
  // println(mary learnsScala) // deprecated in scala 3
}
