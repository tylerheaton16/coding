//advent of code 2023-1
val lines = scala.io.Source.fromFile("cal.txt").getLines().toList

val spelledOutNumbers = List("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

val spelledOutPattern = spelledOutNumbers.mkString("|").r



  val firstWord = firstSpelledOut(lines(0))
  val fwi = firstSpelledOutIndex(lines(0))
  val firstDig = firstDigit(lines(0))
  val fdi = firstDigitlIndex(lines(0), firstDig)

  val rwi = lastSpelledOutIndex(lines(0))
  val rWord = if (rwi >= 0) lastSpelledOut(lines(0)) else -1
  val rdi = lastDigitWithIndex(lines(0))
  val rDig = if (rdi >= 0) lines(0)(rdi).asDigit else -1

  val matchesIter = {
  for {
    lineTail <- lines(0).tails
    oneMatch <- spelledOutPattern.findPrefixOf(lineTail)
  }
  yield {
    oneMatch
  }
  }
val matches = matchesIter.toList


val calcVal: List[Int] = lines.map { line =>
  val firstWord = firstSpelledOut(line)
  val fwi = firstSpelledOutIndex(line)
  val firstDig = firstDigit(line)
  val fdi = firstDigitlIndex(line, firstDig)

  val rwi = lastSpelledOutIndex(line)
  val rWord = if (rwi >= 0) lastSpelledOut(line)
  val rdi = lastDigitWithIndex(line)
  val rDig = if (rdi >= 0) line(rdi).asDigit else -1

  val matchesIter = {
  for {
    lineTail <- line.tails
    oneMatch <- spelledOutPattern.findPrefixOf(lineTail)
  }
  yield {
    oneMatch
  }
  }
val matches = matchesIter.toList



  val p1: Int = (fwi, fdi) match {
    case (fwi, fdi) if ((fwi < fdi) && (fwi < 0)) => firstDig
    case (fwi, fdi) if ((fwi > fdi) && (fdi < 0)) => convertSpelledOutToInt(spelledOutNumbers, firstWord).get
    case (fwi, fdi) if (fwi < fdi) => convertSpelledOutToInt(spelledOutNumbers, firstWord).get
    case (fwi, fdi) if (fdi < fwi) => firstDig
    case _ => 0
  }

  val p2: Int = (rwi, rdi) match {
    case (rwi, rdi) if ((rwi < rdi) ) => rDig
    case (rwi, rdi) if ((rwi > rdi) ) => convertSpelledOutToInt(spelledOutNumbers, rWord).get
    case (rwi, rdi) if (rwi < rdi) => convertSpelledOutToInt(spelledOutNumbers, rWord).get
    case (rwi, rdi) if (rdi < rwi) => rDig
    case _ => 0
  }

  println(p1)
  println(p2)
  (p1.toString ++ p2.toString).toInt
}


val total = calcVal.sum

def firstSpelledOut(s: String) = spelledOutPattern.findFirstIn(s).getOrElse(-1)
def firstSpelledOutIndex(s: String) = spelledOutPattern.findFirstMatchIn(s).map(_.start).getOrElse(-1)
def firstDigit(s: String): Int = s.find(_.isDigit).getOrElse(-1).toString.toInt
def firstDigitlIndex(s: String, i: Int): Int = s.indexOf(i.toString)

def lastSpelledOut(s: String) = {
  val allMatches = spelledOutPattern.findAllIn(s).toList
  val res = if (allMatches.nonEmpty) allMatches.last else -1
  res
}

def lastSpelledOutIndex(s: String) = {
  val allMatches = spelledOutPattern.findAllMatchIn(s).toList
  val res = if (allMatches.nonEmpty) allMatches.last.start else -1
  res
  }

lastDigitWithIndex(lines(0))
val vvv = lines(0)(lastDigitWithIndex(lines(0))).asDigit
def lastDigitWithIndex(s: String): Int = {
  val lastDigitIndex = s.lastIndexWhere(_.isDigit)  // Find the index of the last digit
  if (lastDigitIndex != -1) {
    (lastDigitIndex)  // Return the digit and its index
  } else {
    -1
  }
}


def convertSpelledOutToInt(spelledOutNumbers: List[String], input: Any): Option[Int] = {
  spelledOutNumbers.indexOf(input) match {
    case -1 => None  // If the number is not found, return None
    case index => Some(index)  // Return the index as the corresponding integer
  }
}
