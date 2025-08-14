package lectures.part3fp

import scala.util.Random

object Sequences extends App {

  //Seq - very general interface for data structures that have well defined order. Can also be indexed
    // supports apply, iterator, length, reverse, concat, reverse, .....

  val aSequence = Seq(2,1,3,4)
  println(aSequence)
  println(aSequence.reverse)
  println(aSequence(2))
  println(aSequence ++ Seq(5,6,7))
  println(aSequence.sorted)

  // Ranges
  val aRange: Seq[Int] = 1 until 10
  aRange.foreach(println)

  (1 to 10).foreach(x => println("hello")) // this works 10 times

  // Lists
  val aList = List(1,2,3)
  val prepend = 42 :: aList
  val preAndAppend = 42 +: aList :+ 89
  println(prepend)
  println(preAndAppend)

  // a list of 5 "apples"
  val apples5 = List.fill(5)("apples") //curried function which takes a number and value
  println(apples5)

  println(aList.mkString("-|-"))

  // Arrays

  val numbers = Array(1,2,3,4)
  val threeElements = Array.ofDim[Int](3) //ofDim is an array method
  println(threeElements)
  threeElements.foreach{ x => println(x)} //default values

  // Mutation
  // syntatic sugar for numbers.update(2,0)
  numbers(2) = 0 //update the value at index 2 with the value of 0
  //numbers.update(3,1) //update the value at index 2 with the value of 0
  println(numbers.mkString(" "))

  // arrays and seq
  val numbersSeq: Seq[Int] = Array(1,2,3,4) //implicit conversion
  println(numbersSeq)

  // vectors
  val vector: Vector[Int] = Vector(1,2,3)
  println(vector)

  //vectors vs lists

  val maxRuns = 1000
  val maxCapacity = 1000000
  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new Random
    val times = for {
      iteration <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      // operation
      collection.updated(r.nextInt(maxCapacity), 0)
      System.nanoTime() - currentTime
    }
    times.sum * 1.0 / maxRuns // average time for this collection to be upated at a random index
  }

  val numbersList = (1 to maxCapacity).toList
  val numbersVector = (1 to maxCapacity).toVector

  // pro - keeps reference to tail
  // con - updating element in the middle takes a long time
  println(getWriteTime(numbersList))

  // pro - depth of the tree is small
  // con - needs to replace an entire 32-element chunk
  println(getWriteTime(numbersVector))
}

