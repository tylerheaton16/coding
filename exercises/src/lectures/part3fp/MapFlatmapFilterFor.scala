package lectures.part3fp

object MapFlatmapFilterFor extends App {
  val list = List(1, 2, 3)
  println(list)
  println(list.head)
  println(list.tail)

  // Map
  println(list.map(_ + 1))
  println(list.map(_ + " is a number"))

  // filter
  println(list.filter(_ % 2 == 0))

  // flatMap
  val toPair: Int => List[Int] =
    (x: Int) => List(x, x + 1) // creates a new list
  println(list.flatMap(toPair))

  /*
  1. print all combinations between two lists
   */
  val numbers = List(1, 2, 3, 4)
  val chars   = List('a', 'b', 'c', 'd')
  val colors  = List("black", "white")

  // List("a1", "a2", ... "d4")
  // "iterating" in functional
  val nList = numbers.flatMap { num =>
    chars.flatMap { char => colors.map(color => s"$char$num$color") }
  }
  println(nList)

  // foreach
  list.foreach(println)

  // for-comprehensions. Makes these massive loops make sense to read
  val forCombinations = for {
    n <- numbers
    if n % 2 == 0 // filterse to only even numbers. We call these GUARDS
    c     <- chars
    color <- colors
  } yield (s"$c$n$color") // this is MUCH more readable
  println(forCombinations)

  for {
    n <- numbers
  }
  println(n)
  // syntax overload
  list.map { x =>
    x * 2
  }

  /*
  1. MyList supports for comprehensions
    map(f: A => B) => MyList[B]
    filter(p: A => Boolean) => MyList[A]
    flatMap(f: A => MyList[B]) => MyList[B]
  2. A small collection of at MOST one element - called it Maybe[+T].
    - map, flatMap, filter
  */

}
