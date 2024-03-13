package lectures.part2oop

import java.security.Permission

enum Permissions {
  case READ, WRITE, EXECUTE, NONE

  // add fields/methods
  def openDocument(): Unit = {
    if (this == READ) println("Opening Document")
    else println("Reading not allowed")
  }
}

enum PermissionsWithBits(bits: Int) {
  case READ    extends PermissionsWithBits(4) // 1000
  case WRITE   extends PermissionsWithBits(2) // 0010
  case EXECUTE extends PermissionsWithBits(1) // 0001
  case NONE    extends PermissionsWithBits(0) // 0000
}

object PermissionsWithBits {
  def fromBits(bits: Int): PermissionsWithBits = // whatever
    PermissionsWithBits.NONE
}

// standard API
val somePermissions: Permissions = Permissions.READ
val somePermissionsOrdinal       = somePermissions.ordinal
val allPermissions =
  PermissionsWithBits.values // array of all possible vlaues of the enum
val readPermission: Permissions =
  Permissions.valueOf("READ") // Permissions.READ

object Enums extends App {

  somePermissions.openDocument()
  println(somePermissionsOrdinal)
  println(allPermissions)
  println(readPermission)
}
