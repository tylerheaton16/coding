package lectures.part3fp

import exercises.ListTest
import scala.collection.immutable.Stream.cons
import scala.annotation.tailrec

object TuplesAndMaps extends App {
  // tuples = finite ordered kinda like "lists"

  val aTuple =
    new Tuple2(2, "hello, scala") // Tuple2[Int, String] = (Int, String)
  // can group at most 22 elements - used with function types which can go from 0 to 22
  // what can we do with a tuple?

  println(aTuple._1) // first element
  println(aTuple._2) // second element
  println(aTuple.copy(_2 = "goodbye java"))
  println(aTuple.swap) // ("hello, scala", 2)

  // Maps are collections used to associate things with other things
  // So keys -> values (key value pairs). The data that corresponds to the keys
  val aMap: Map[String, Int] = Map() // String is key, Int is value

  val phonebook = Map(("Jim", 555), "Daniel" -> 789).withDefaultValue(
    -1
  ) // syntatic sugar for the tuple I wrote earlier
  // a -> b is syntatic sugar for (a,b)

  println(phonebook)

  // map operations
  println(phonebook.contains("Jim"))
  println(
    phonebook("Jim")
  ) // Jim is the key. It's value is  555. So this will get the value for its key pair
  println(phonebook("Mary")) // this doesn't exist so it will crash
  // withDefaultValue will return -1 as I set above for a key which does not exist

  // add a pairing
  val newPairing   = "Mary" -> 768
  val newPhoneBook = phonebook + newPairing
  println(newPhoneBook)

  // functionals on maps
  // map, flatMap, filter

  println(phonebook.map(pair => pair._1.toLowerCase -> pair._2))

  // filterKeys // phonebook.view.filterKeys(...).toMap
  println(phonebook.view.filterKeys(x => x.startsWith("J")).toMap)

  // mapValues
  println(phonebook.view.mapValues(number => number * 10).toMap)

  // conversions to other collections
  println(phonebook.toList)
  println(List(("Daniel", 555)).toMap)
  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  println(names.groupBy(name => name.charAt(0)))

  /*
   1. What would happen if in the "toLowerCase" had 2 original entries "Jim" -> " 555" and "JIM" -> 900
      println(phonebook.map(pair => pair._1.toLowerCase -> pair._2)) what would happen in here
        -answer: it would update based on the most recent.
   2. Overy simplified social network based on maps
      - Person = String
      - add a person to the network
      - remove a person from the network
      - friend
      - unfriend
      # stats
      - number of friends of a given person
      - person with the most friends
      - how many people have NO friends
      - if there is a social connection between two people (direct or not) - if I know you and you know someone else, we have a social connection
   */

  // friend will be a string. friends of them will be a List[String]
  // friend network. Person who is the mutual friend
  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]]  = {
    network + (person -> Set())

  }
  def friend(network: Map[String, Set[String]], a: String, b: String) = {
    val friendA = network(a)
    val friendB = network(b)
    network + (a -> (friendA + b)) + (b -> (friendB + a)) // + does not work unless it is a Set[String]. Use :+ or +: if using List
    // this makes sense.
    /*
    network + ("Jake" -> ("Hannah", "John")) + ("John" -> ("Ash", "Jake")) // john and cake are now eachothers friends in their networks
    */
  }
  def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendA = network(a)
    val friendB = network(b)
    network + (a -> (friendA - b)) + (b -> (friendB - a))
    // this makes sense.
  }
  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    // must first remove the friends from both networks
    // we must remove all friends in the key -> value pair (remove values) before removing the key.
    // So, make an auxillary function which will remove all values, then we can remove the key. i.e. no floating values for a non-existent key
    def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] = {
      //friends: = friends that need unfriending
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, unfriend(networkAcc, person, friends.head))
    }

    val unfriended = removeAux(network(person), network)
    unfriended - person

  }
  val empty: Map[String, Set[String]] = Map()
  val network: Map[String, Set[String]] = add(add(empty, "Bob"), "Mary") // This starts to cause issues when you dont define the type for def
  println(network)
  println(friend(network, "Bob", "Mary"))
  println(unfriend(friend(network, "Bob", "Mary"), "Bob", "Mary"))
  println(remove(friend(network, "Bob", "Mary"), "Bob"))

  // Jim, Bob, Mary
  val people = add(add(add(empty, "Bob"), "Mary"), "Jim")
  val jimbob = friend(people, "Bob", "Jim")
  val testNet = friend(jimbob, "Bob", "Mary")
  println(testNet)

  def nFriends(network: Map[String, Set[String]], person: String): Int = {
    if (!network.contains(person)) 0
    else network(person).size
  }
  println(nFriends(testNet, "Bob"))

  def mostFriends(network: Map[String, Set[String]]): String = {
    network.maxBy(pair => pair._2.size)._1
    //maxBy finds the largest value which meets the anonymous function. So we found the largest friend size
  }
  println(mostFriends(network))
  def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int = {
    //network.view.filterKeys(k => network(k).isEmpty).toMap.size // testing keys
    network.count(pair => pair._2.isEmpty) // testing values
  }
  println(nPeopleWithNoFriends(testNet))

  def socialConnection(network: Map[String, Set[String]], a: String, b: String): Boolean = {
    // breadth first search
    // hard function to think about.
    @tailrec
    def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean = {
      if (discoveredPeople.isEmpty) false
      else {
        val person = discoveredPeople.head
        if (person == target) true
        else if (consideredPeople.contains(person)) bfs(target,consideredPeople, discoveredPeople.tail)
        else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))
      }

    }
    bfs(b ,Set(), network(a) + a)
  }
  println(socialConnection(testNet, "Mary", "Jim"))
  println(socialConnection(network, "Mary", "Bob"))
}
