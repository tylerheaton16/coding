package lectures.part1basics

object Functions extends App {

  def aFunction(a: String, b: Int) = {
    a + " " + b
  }

  println(aFunction("hello", 3))

  def aParameterlessFunction(): Int = 42
  println(aParameterlessFunction())
  // println(aParameterlessFunction)

  // recursive functions need return types!
  def aRepeatedFunction(aString: String, n: Int): String = {
    if (n == 1) aString
    else aString + aRepeatedFunction(aString, n - 1)
  }

  println(aRepeatedFunction("hello", 3))

  // WHEN YOU NEED LOOPS, USE RECURSION

  def aFunctionWithSideEffects(aString: String): Unit = {
    println(aString)
  }

  def aBigFunction(n: Int): Int = {
    def aSmallerFunction(a: Int, b: Int): Int = a + b

    aSmallerFunction(n, n - 1) // the return type.
  }

  /*
  1. A greeting function (name, age) => "Hi, my name is $name and I am $age years old"
  2. Factorial function. 1 * 2 * 3 .. * n - recursive function
  3. A fibonacci function
      f(1) = 1
      f(2) = 1
      f(n) = f(n-1) + f(n-2)
  4. Tests if a number is prime.
   */

  def aGreetingFunction(name: String, age: Int): Unit = {
    val sentence = "Hi, my name is " + name + ", and I am " + age + " years old"
    println(sentence)
  }

  aGreetingFunction("Tyler", 29)

  def aFactorialFunction(n: Int): Int = {
    if (n == 0) 1
    else n * aFactorialFunction(n - 1)
  }
  println(aFactorialFunction(5))

  def aFibonacciFunction(n: Int): Int = {
    if (n == 0) return 1
    else if (n == 1) 1
    else if (n == 2) 1
    else aFibonacciFunction(n - 1) + aFibonacciFunction(n - 2)
  }
  println(aFibonacciFunction(8))

  def isPrime(n: Int): Boolean = {
    def isPrimeUntil(t: Int): Boolean = {
      if (t <= 1) true
      else n % t != 0 && isPrimeUntil(t - 1)
    }
    isPrimeUntil(n / 2)
  }
  println(isPrime(37))
  println(isPrime(37 * 17))
}
