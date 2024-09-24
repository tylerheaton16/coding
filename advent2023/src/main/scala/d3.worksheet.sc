 import scala.util.matching.Regex
/*
  notes -
  1) parse each line until we see a digit. It ends when the next character is not a digit but a `.` or symbol
  2) We only care about symbols around a digit. So, the previous list and the list after.
        So, if we read list[5], we need to look at list[4] and list[6]
            if we read list[0], we need to look at list[1]

  3) If we look at list[5] index 6 digit
       - list[4] index 5,6,7
       - list[5] index 5, ,7
       - list[6] index 5,6,7

    467..114..
    ...*......
    ..35..633.
    ......#...
    617*......
    .....+.58.
    ..592.....
    ......755.
    ...$.*....
    .664.598..
 */

val parts = scala.io.Source.fromFile("files/d3_parts.txt").getLines().toList
val newList = parts.map(_.toList)
val numR = "\\d+".r
val tt = parts(0)
val aa = numR.findAllIn(parts(0)).toList
println(aa(1))
val n = parts(0)(1).toString.toInt
val a = new Regex("[~!@#$^%&*\\(\\)_+={}\\[\\]|;:\"'<,>.?`/\\\\-]")

val b = "a@b4?"

val c = a.findFirstIn(b)
val num = numR.findAllIn(parts(0)).toList.map{myval => myval.toInt}
val index = parts(0).indexWhere(_ == 4.toString)

def getNumber(s: String ) = {
  val num = numR.findAllIn(s).toList.map{myval => myval.toInt}

}
