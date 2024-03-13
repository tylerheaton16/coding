package lectures.part1basics

import scala.annotation.tailrec

object DefaultArgs extends App {
  @tailrec
  def trFact(n: Int, acc: Int = 1): Int = {
    if (n <= 1) acc
    else trFact(n - 1, n * acc)
  }

  val fact10 = trFact(10, 1) // acc pollutes the function signature because it
  // will always be 1. Why not just have 10
  val fact102 = trFact(10)

  def savePicture(
      format: String = "jpg",
      width: Int = 1920,
      height: Int = 1080
  ): Unit = println("saving picture")
  savePicture(width = 800)

  /*
    1. pass in every leading argument ( of course this will work but needs to be in order)
    2. name the arguments (width = 800). This solves this problem. 2 better than 1 every time
   */

  savePicture(
    height = 600,
    width = 800,
    format = "bmp"
  ) // this works because we have provided clarification

}
