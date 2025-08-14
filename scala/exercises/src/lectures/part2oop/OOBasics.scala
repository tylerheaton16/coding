package lectures.part2oop

object OOBasics extends App {
  // val person = new Person("John", 26)
  //// println(person.age) //age is a class parameter. NOT a class member
  // println(person.age)
  // println(person.x)
  // println(person.greet("Daniel"))
  // println(person.greet())

  val writer   = new Writer("John", "Doe", 1994)
  val imposter = new Writer("John", "Doe", 1994)
  val novel    = new Novel("Hobbits", 2005, writer)
  println(writer.fullName())
  println(novel.authorAge)
  println(novel.isWrittenBy(writer))
  println(novel.copy(2010))

  val counter = new Counter
  counter.inc.print
  counter.inc.inc.inc.print
  counter.inc(10).print
  counter.inc.inc(5).print
}

// constructors. Every instance of Person must have a name and age
class Person(name: String, val age: Int = 0) {
  // body
  val x = 2
  println(1 + 3)

  // method - a function defined in a class
  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")

  // overloading - defines the same function but with different signatures
  def greet(): Unit = println(s"Hi, I am $name")
  // def greet(): Int = 42 // can't do this because greet() was already defined. same parameter list

  // multiple constructors
  def this(name: String) = this(name, 0)
  def this() = this("John Doe")

  // class parameters are NOT FIELDS
  // must had "val" to the constructor to convert it from a parameter to a field

  /*
  Novel and a Writer class
  Writer: first name, surname, year of birth
    - method called fullName - returns concatenation of first name and surname
  Novel: name, year of release, author (type writer)
    - authorAge which returns age of author at the year of release
    - isWrittenBy(author)
    - copy (new year of release) = new instance of Novel
   */
  /*
  Counter class
    - receives an int value
    - method current count
    - method to increment/decrement => new Counter
    - overload inc/dec to receive an amount => new Counter
   */

}
class Novel(name: String, yearOfRelease: Int, author: Writer) {
  def authorAge = {
    yearOfRelease - author.yearOfBirth
  }
  def isWrittenBy(author: Writer) = author == this.author
  def copy(newYearOfRelease: Int) = {
    new Novel(name, newYearOfRelease, author)
  }
}
class Writer(firstName: String, surName: String, val yearOfBirth: Int) {
  def fullName(): String = {
    this.firstName + " " + this.surName
  }
}

class Counter(val count: Int = 0) {
  def inc = {
    println("incrementing")
    new Counter(count + 1) // immutability
  }

  def dec = {
    println("decrementing")
    new Counter(count - 1)
  }

  def inc(n: Int): Counter = {
    if (n <= 0) this
    else inc.inc(n - 1)
  }

  def dec(n: Int): Counter =
    if (n <= 0) this
    else dec.dec(n - 1)

  def print = println(count)
}
