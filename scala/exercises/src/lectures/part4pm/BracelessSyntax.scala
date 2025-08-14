package lectures.part4pm

object BracelessSyntax extends App {

  // if - expressions
  val anIfExpression = if (2 > 3) "bigger" else "smaller"

  // java-style
  val anIfExpression_v2 =
    if (2 > 3) {
      "bigger"
    } else {
      "smaller"
    }

  // compact style
  val anIfExpression_v3 =
    if (2 > 3) "bigger"
    else "smaller"

  // scala 3
  val anIfExpression_v4 =
    if 2 > 3 then
      "bigger" // needs a higher indentation than the if part. Like python
    else "smaller"

  val anIfExpression_v5 =
    if 2 > 3 then
      val result = "bigger"
      result
    else
      val result = "smaller"
      result

  val anIfExpression_v6 = if 2 > 3 then "bigger" else "smaller"

  // for comprehensions
  val aForComprehension = for {
    n <- List(1, 2, 3)
    s <- List("black", "white")
  } yield s"$n$s"

  // scala 3
  val aForComprehension_v2 =
    for
      n <- List(1, 2, 3)
      s <- List("black", "white")
    yield s"$n$s"

  // pattern matching
  val meaningOfLife = 42
  val aPatternMatch = meaningOfLife match {
    case 1 => "the one"
    case 2 => "double or nothing"
    case _ => "something else"
  }

  // scala 3
  val aPatternMatch_v2 = meaningOfLife match
    case 1 => "the one"
    case 2 => "double or nothing"
    case _ => "something else"

  // methods without braces
  def computeMeaningOfLife(arg: Int): Int =
    val partialResult = 40

    // note this works because significant indentation

    partialResult + 2

  // class definition with significant indentation (same for traits, objects, enums etc)
  class Animal: // need the : to imply this
    def eat(): Unit =
      println("I'm eating")
    end eat // helps us know when the def ends. Not needed

    def grow(): Unit =
      println(computeMeaningOfLife((78)))

    // 3000 more lines of code

  end Animal // allows us to show where the end is
  // can use for if, match, for, methods, classes, traits, enums, objects, and anything with significant indentation

  // anonymous classes
  val aSpecialAnima = new Animal: // need colon here to use scala 3 syntax too
    override def eat(): Unit = println("I'm special")

  // indentation = strictly larger indentation
  // 3 spaces + 2 tabs > 2 spaces + 2 tabs
  // 3 spaces + 2 tabs > 3 spaces + 1 tab
  // 3 tabs + 2 spaces ??? 2 tabs + 3 spaces // compiler will be confused
  println(anIfExpression_v5)
}
