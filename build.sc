import mill._
import mill.scalalib._
import mill.scalalib.scalafmt._

object exercises extends ScalaModule {
  def scalaVersion = "3.2.2"
  def scalacOptions = Seq(
    "-unchecked",
    "-deprecation",
    "-language:reflectiveCalls",
    "-feature",
    "-Xcheckinit",
    "-Ywarn-dead-code",
    "-Ywarn-unused",
    "-Ymacro-annotations"
  )
}
object advent2023 extends ScalaModule {
  def scalaVersion = "2.13.12"
  def scalacOptions = Seq(
    "-unchecked",
    "-deprecation",
    "-language:reflectiveCalls",
    "-feature",
    "-Xcheckinit",
    "-Ywarn-dead-code",
    "-Ywarn-unused",
    "-Ymacro-annotations"
  )
}
