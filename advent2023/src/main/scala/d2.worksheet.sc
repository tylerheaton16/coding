// The bag has 12 red cubes, 13 green cubes, and 14 blue cubes
// Read d2_games.txt and parse it into game, ID, GROUP , semicolon, GROUP ... END with no semicolon
import scala.collection.mutable.ListBuffer

val games = scala.io.Source.fromFile("d2_games.txt").getLines().toList

val gameR = "Game \\d+".r
val numR = "\\d+".r
val semiR = "\\;".r
val blueR = "\\d+ blue".r
val greenR = "\\d+ green".r
val redR = "\\d+ red".r
var cntList = 0
val listB = ListBuffer(0)

val chkR = 12
val chkG = 13
val chkB = 14


games.foreach( gameSet => {
  val gameNumber = numR.findFirstIn(gameR.findFirstIn(gameSet).get).get.toInt
  val chunks = gameSet.split("\\s*;\\s*")
  val size = chunks.size

  //check Red
  val yesR = for (i <- 0 to (size-1)) yield {
    val checkRed = redR.findFirstIn(chunks(i))
    val maybeRed = checkRed.fold(None: Option[String])(t => Some(t))
    val getMaybeRedNum = numR.findFirstIn(maybeRed.getOrElse("0")).get.toInt
    val yesR = if(getMaybeRedNum <= chkR) {true} else {false}
    yesR
  }
  //check green
  val yesG = for (i <- 0 to (size-1)) yield {
    val checkGreen = greenR.findFirstIn(chunks(i))
    val maybeGreen = checkGreen.fold(None: Option[String])(t => Some(t))
    val getMaybeGreenNum = numR.findFirstIn(maybeGreen.getOrElse("0")).get.toInt
    val yesG = if(getMaybeGreenNum <= chkG) {true} else {false}
    yesG
  }
  //check blue
  val yesB = for (i <- 0 to (size-1)) yield {
    val checkBlue = blueR.findFirstIn(chunks(i))
    val maybeBlue = checkBlue.fold(None: Option[String])(t => Some(t))
    val getMaybeBlueNum = numR.findFirstIn(maybeBlue.getOrElse("0")).get.toInt
    val yesB = if(getMaybeBlueNum <= chkB) {true} else {false}
    yesB
  }

  val valid = yesR.forall(identity) && yesG.forall(identity) && yesB.forall(identity)

  if (valid) {
    cntList = cntList + gameNumber
  }

  val allR = for (i <- 0 to (size-1)) yield {
    val checkRed = redR.findFirstIn(chunks(i))
    val maybeRed = checkRed.fold(None: Option[String])(t => Some(t))
    val getMaybeRedNum = numR.findFirstIn(maybeRed.getOrElse("0")).get.toInt
    getMaybeRedNum
  }
  val lR = allR.toList.max

  //check green
  val allG = for (i <- 0 to (size-1)) yield {
    val checkGreen = greenR.findFirstIn(chunks(i))
    val maybeGreen = checkGreen.fold(None: Option[String])(t => Some(t))
    val getMaybeGreenNum = numR.findFirstIn(maybeGreen.getOrElse("0")).get.toInt
    getMaybeGreenNum
  }
  val lG = allG.toList.max

  //check blue
  val allB = for (i <- 0 to (size-1)) yield {
    val checkBlue = blueR.findFirstIn(chunks(i))
    val maybeBlue = checkBlue.fold(None: Option[String])(t => Some(t))
    val getMaybeBlueNum = numR.findFirstIn(maybeBlue.getOrElse("0")).get.toInt
    getMaybeBlueNum
  }
  val lB = allB.toList.max

  val power = lR * lG * lB
  listB += power
})

val total = cntList
val tot = listB.sum


