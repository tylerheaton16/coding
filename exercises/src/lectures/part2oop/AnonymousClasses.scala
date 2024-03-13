package lectures.part2oop

import lectures.part1basics.StringOps.name

object AnonymousClasses extends App {
  abstract class Animal {
    def eat: Unit
  }

  // anonymous class
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("ahahahahah")
  }

  /*
  Equivalent with above. Compiler does this behind the scenes
  class AnonymousClasses$$anon$1 extends Animal {
    override def eat: Unit = println("ahahahahah")
  }
  val funnAnimal: Animal = new AnonymousClasses$$anon$1
   */

  println(funnyAnimal.getClass)

  class Person(name: String) {
    def sayHi: Unit = println(s"Hi, my name is $name, how can I help you?")
  }

  val jim = new Person("Jim") {
    override def sayHi: Unit = println(
      s"Hi, my name is $name, how can I help you?"
    )
  }

  // Can do this with traits as well

  trait Trainer {
    def trainme: Unit
  }

  val myTrainer: Trainer = new Trainer {
    override def trainme: Unit = println("I train you")
  }
  println(myTrainer.trainme)

  /*
    Exercises:
    1.  Generic trait MyPredicate[-T] with a little method test(T) => Boolean
    2.  Generic trait MyTransformer[-A, B] with a method transform(A) => B
    3.  MyList:
        - map(transformer) => MyList
        - filter(predicate) => MyList
        - flatMap(transformer from A to MyList[B]) => MyList[B]

        class EvenPredicate extends MyPredicate[Int]
        class StringToIntTransformer extends MyTransformer[String, Int]

        [1,2,3].map(n * 2) = [2,4,6]
        [1,2,3,4].filter(n % 2) = [2,4]
        [1,2,3].flatMap(n => [n, n+1]) => [1,2,2,3,3,4]
   */
}
