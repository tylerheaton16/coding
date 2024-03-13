package lectures.part2oop

object Exceptions extends App {

  val x: String = null
  // println(x.length)
  // this ^^ will crash with a Null Pointer Exception (NPE)

  // 1. throwing and catching exceptions

  // val aWeirdValue: String = throw new NullPointerException

  // throwable classes extend the Throwable class.
  // Exception and Error are the major Throwable subtypes.

  // 2. How to catch exceptions
  def getInt(withExceptions: Boolean): Int = {
    if (withExceptions) throw new RuntimeException("No Int for you!")
    else 42
  }

  val potentialFail =
    try {
      // code that might throw
      getInt(false)
    } catch {
      case e: RuntimeException => 43
    } finally {
      // code that will get executed NO MATTER WHAT
      // optional
      // does not influence the return type of this expression
      // use finally only for side effects
      // like logging to a file
      println("finally")
    }

  println(potentialFail)

  // 3. how to define your own exceptions
  class MyException extends Exception
  val exception = new MyException

  // throw exception

  /*
  1. Crash your program with an OutOfMemoryError
  2. Stack with SOError
  3. PocketCalculator
    - adds(x,y)
    - subtract(x,y)
    - multiply(x,y)
    - deivide(x,y)

    Throw
      - OverFlowException if add(x,y) exceeds int.MAX_VALUE
      - UnderFlowException if subtract(x,y) exceeds Int.MIN_VALUE
      - MathCalculationException for division by 0
   */
  // OOM
  // val array = Array.ofDim(Int.MaxValue)

  // Stack Overflow (SO)
  // def infinite: Int = 1 + infinite
  // val noLimit = infinite

  class OverFlowException extends RuntimeException
  class UnderFlowException extends RuntimeException
  class MathCalculationException extends RuntimeException("Division by 0")

  object PocketCalculator {
    def add(x: Int, y: Int) = {
      val result = x + y
      // if (result > Int.MaxValue)  // this will never work because MaxValue is always greater
      if (x > 0 && y > 0 && result < 0) throw new OverFlowException
      else if (x < 0 && y < 0 && result > 0) throw new UnderFlowException
      else result
    }
    def subtract(x: Int, y: Int) = {
      val result = x - y
      if (x >0 && y < 0 && result < 0) throw new OverFlowException
      else if (x < 0 && y > 0 && result > 0) throw new UnderFlowException
      else result
    }
    def multiply(x: Int, y: Int) = {
      val result = x * y
      if (x > 0 && y > 0 && result < 0) throw new OverFlowException
      else if (x < 0 && y < 0 && result < 0) throw new OverFlowException
      else if (x > 0 && y < 0 && result > 0) throw new UnderFlowException
      else if (x < 0 && y > 0 && result > 0) throw new UnderFlowException
      else result
    }
    def divide(x: Int, y: Int) = {
      if (y == 0) throw new MathCalculationException
      else x / y
    }

  }
  //println(PocketCalculator.add(Int.MaxValue, 10))
  println(PocketCalculator.divide(2,0))
}
