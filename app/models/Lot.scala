package models

/**
 * Created with IntelliJ IDEA.
 * @author <a href="mailto:wstarcev@gmail.com">Vasilii Startsev</a>
 *         Date: 25.08.13
 *         Time: 19:10
 */
case class Lot(

  name : Option[String],

  rooms : Option[Int],

  level : Option[Int],

  description : Option[String]

)
