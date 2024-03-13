package lectures.part1basics

object CBNvsCBV extends App {

  def calledByValue(x: Long): Unit = {
    println("by value: " + x) // x = 134932509463800
    println("by value: " + x) // x = 134932509463800
  }

  def calledByName(x: => Long): Unit = {
    println("by name: " + x) // x = System.nanoTime()
    println("by name: " + x) // x = System.nanoTime()
  }

  calledByValue(System.nanoTime())
  calledByName(System.nanoTime())

  def infinite(): Int               = 1 + infinite()
  def printFirst(x: Int, y: => Int) = println(x)

  printFirst(
    34,
    infinite()
  ) // this does not seg fault because infinite is delayed until used. println(x) doesn't called y
}
