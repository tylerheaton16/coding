package lectures.part1basics

import scala.annotation.tailrec
import scala.runtime.stdLibPatches.language.`3.1`

object Recursion extends App {
  def aFactorialFunction(n: Int): Int = {
    if (n <= 0) 1
    else {
      println(
        "Computing factorial of " + n + " I first need factorial of " + (n - 1)
      )
      val result =
        n * aFactorialFunction(n - 1) // this is not the last expression
      println("Computed factorial of " + n)

      result
    }
  }
  println(aFactorialFunction(5))

  def anotherFactorial(n: Int): BigInt = {
    @tailrec // tells the compiler it should be tail recursive.
    def factHelper(x: Int, accumulator: BigInt): BigInt = {
      if (x <= 1) accumulator
      else
        factHelper(
          x - 1,
          x * accumulator
        ) // this does not have to give back a result to the stack. Therefore, no intermediate
      // values are stored. So, we do not stack trace. This is TAIL RECURSION
      // use recursive call as the LAST expression
    }
    factHelper(n, 1)
  }
  println(anotherFactorial(10))
  // WHEN YOU NEED LOOPS, USE _TAIL_ RECURSION
  /*
   1. Concatenate a string n times
   2. IsPrime function tail recursive
   3. Fibonacci function. tail recursive
   */

  def concatStringN(name: String, n: Int): String = {
    @tailrec
    def concatHelper(x: Int, acc: String): String = {
      if (x <= 1) acc
      else concatHelper(x - 1, acc + name)
    }
    concatHelper(n, "Tyler")
  }
  println(concatStringN("Tyler", 5))

  def isPrimeTailRec(n: Int): Boolean = {
    @tailrec
    def isPrimeUntil(t: Int, isStillPrime: Boolean): Boolean = {
      if (!isStillPrime) false
      else if (t <= 1) true
      else isPrimeUntil(t - 1, n % t != 0 && isStillPrime)
    }
    isPrimeUntil(n / 2, true)
  }
  println(isPrimeTailRec(37))

  def aFibonacciFunction(n: Int): Int = {
    @tailrec
    def fibHelper(i: Int, acc1: Int, acc2: Int): Int = {
      if (i >= n) acc1
      else fibHelper(i + 1, acc1 + acc2, acc1)
    }
    if (n <= 2) 1
    else fibHelper(2, 1, 1)
  }
  println(aFibonacciFunction(8))
}
