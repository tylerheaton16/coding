//package exercises
//import math.Fractional.Implicits.infixFractionalOps
//import math.Integral.Implicits.infixIntegralOps
//import math.Numeric.Implicits.infixNumericOps
//import lectures.part1basics.Recursion.anotherFactorial
//
//abstract class MyListOOP[+A] {
//  /*
//  head = first element of the list
//  tail = remainder of the list
//  isEmpty = is this list empty
//  add(int) => new list with this element added
//  override toString => a string representation of the list
//   */
//  def head: A
//  def tail: MyListOOP[A]
//  def isEmpty: Boolean
//  def add[B >: A](n: B): MyListOOP[B]
//  def printElements: String
//
//  // polymorphic call
//  override def toString: String = "[" + printElements + "]"
//  def map[B](transformer: MyTransformer[A, B]): MyListOOP[B]
//  def flatMap[B](transformer: MyTransformer[A, MyListOOP[B]]): MyListOOP[B]
//  // concatenation
//  def ++[B >: A](list: MyListOOP[B]): MyListOOP[B]
//  def filter(predicate: MyPredicate[A]): MyListOOP[A]
//  // def toString: String
//}
//
//case object EmptyOOP extends MyListOOP[Nothing] {
//  override def head: Nothing         = throw new NoSuchElementException
//  override def tail: MyListOOP[Nothing] = throw new NoSuchElementException
//  override def isEmpty: Boolean      = true
//  override def add[B >: Nothing](n: B): MyListOOP[B] = new ConsOOP(n, EmptyOOP)
//  def printElements: String                       = ""
//  def map[B](transformer: MyTransformer[Nothing, B]): MyListOOP[B] = EmptyOOP
//  def flatMap[B](transformer: MyTransformer[Nothing, MyListOOP[B]]): MyListOOP[B] =
//    EmptyOOP
//  def filter(predicate: MyPredicate[Nothing]): MyListOOP[Nothing] = EmptyOOP
//  def ++[B >: Nothing](list: MyListOOP[B]): MyListOOP[B]             = list
//}
//
//case class ConsOOP[+A](h: A, t: MyListOOP[A]) extends MyListOOP[A] {
//  override def head: A                      = h
//  override def tail: MyListOOP[A]              = t
//  override def isEmpty: Boolean             = false
//  override def add[B >: A](n: B): MyListOOP[B] = new ConsOOP(n, this)
//  def printElements: String = {
//    if (t.isEmpty) { "" + h }
//    else s"${h} ${t.printElements}"
//  }
//  /*
//  [1,2,3].filter(n % 2 == 0) =
//    [2,3].filter( n % 2 == 0) =
//    = new ConsOOP(2, [3].filter(n % 2 == 0))
//    = new ConsOOP(2, Empty.filter(n % 2 == 0))
//    = new ConsOOP(2, Empty)
//   */
//  def filter(predicate: MyPredicate[A]): MyListOOP[A] = {
//    if (predicate.test(h)) new ConsOOP(h, t.filter(predicate))
//    else t.filter(predicate)
//  }
//  /*
//  [1,2,3].map(n * 2)
//    = new ConsOOP(2, [2,3].map(n*2))
//    = new ConsOOP(2, new ConsOOP(4, [3].map(n*2)))
//    = new ConsOOP(2, new ConsOOP(4, new ConsOOP(6, Empty.map(n*2))))
//    = new ConsOOP(2, new ConsOOP(4, new ConsOOP(6, Empty)))
//   */
//  def map[B](transformer: MyTransformer[A, B]): MyListOOP[B] = {
//    new ConsOOP(transformer.transform(h), t.map(transformer))
//  }
//  /*
//  [1,2] ++ [3,4,5]
//  = new ConsOOP(1, [2] ++ [3,4,5])
//  = new ConsOOP(1, new ConsOOP(2, Empty ++ [3,4,5]))
//  new ConsOOP(1, new ConsOOP(2, new ConsOOP(3, new ConsOOP(4, new ConsOOP(5, Empty)))))
//   */
//  def ++[B >: A](list: MyListOOP[B]): MyListOOP[B] = new ConsOOP(h, t ++ list)
//  /*
//  [1,2].flatMap(n => [n, n+1])
//  = [1,2] ++ [2].flatMap(n => [n, n + 1])
//  = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n + 1])
//  = [1,2] ++ [2,3] ++ Empty
//  = [1,2,2,3]
//   */
//  def flatMap[B](transformer: MyTransformer[A, MyListOOP[B]]): MyListOOP[B] = {
//    transformer.transform(h) ++ t.flatMap(transformer)
//  }
//}
//
//trait MyPredicate[-T] {
//  def test(n: T): Boolean
//}
//
//trait MyTransformer[-A, B] {
//  def transform(n: A): B
//}
//
//object ListTestOOP extends App {
//  val listOfIntegers: MyListOOP[Int] = new ConsOOP(1, new ConsOOP(2, new ConsOOP(3, EmptyOOP)))
//  val cloneListOfIntegers: MyListOOP[Int] =
//    new ConsOOP(1, new ConsOOP(2, new ConsOOP(3, EmptyOOP)))
//  val anotherListOfIntegers: MyListOOP[Int] =
//    new ConsOOP(1, new ConsOOP(4, new ConsOOP(5, EmptyOOP)))
//  val listOfStrings: MyListOOP[String] =
//    new ConsOOP("Hello", new ConsOOP("Scala", EmptyOOP))
//
//  println(listOfIntegers.toString)
//  println(listOfStrings.toString)
//
//  println(
//    listOfIntegers
//      .map(new MyTransformer[Int, Int] {
//        override def transform(n: Int): Int = n * 2
//      })
//      .toString
//  )
//
//  println(
//    listOfIntegers
//      .filter(new MyPredicate[Int] {
//        override def test(n: Int): Boolean = n % 2 == 0
//      })
//      .toString
//  )
//
//  println(listOfIntegers ++ anotherListOfIntegers).toString()
//  println(
//    listOfIntegers
//      .flatMap(new MyTransformer[Int, MyListOOP[Int]] {
//        override def transform(n: Int): MyListOOP[Int] =
//          new ConsOOP(n, new ConsOOP(n + 1, EmptyOOP))
//      })
//      .toString
//  )
//
//  println(
//    cloneListOfIntegers == listOfIntegers
//  ) // true because we made ConsOOP a case class
//
//}
