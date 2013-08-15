package models

/**
 * Created with IntelliJ IDEA.
 * @author <a href="mailto:wstarcev@gmail.com">Vasilii Startsev</a>
 *         Date: 15.08.13
 *         Time: 23:55
 */

case class Position(id : String, label : String)

object Position {

  val all : List[Position] = List(
    new Position("one", "Flat #1"),
    new Position("two", "Flat #2"),
    new Position("tree", "Flat #3"),
    new Position("four", "Flat #4"),
    new Position("five", "Flat #5"),
    new Position("6", "Flat #6"),
    new Position("7", "Flat #7")
  )

  def byId(s: String) : Position = all.find( _.id == s ).getOrElse(new Position("void", "default"))

}
