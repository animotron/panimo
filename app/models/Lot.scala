package models

import scala.reflect.io.{Directory, File}
import play.api.libs.json.{JsArray, JsValue, JsObject, Json}
import play.api.libs.json.Json._
import scalax.io.{Output, Resource}
import java.util.UUID
import play.api.data.Form
import play.api.data.Forms._
import scala.io.Source
import java.io.FileNotFoundException

/**
 * Created with IntelliJ IDEA.
 * @author <a href="mailto:wstarcev@gmail.com">Vasilii Startsev</a>
 *         Date: 25.08.13
 *         Time: 19:10
 */

case class Lot(

  id:String,

  location : Location,

  rooms : Int,

  level : Int,

  square: Int,

  spaces:List[Space],

  pets:String,

  manageCo:String,

  communications:String,

  counters:String,

  phone:String, //owner phone

  sub_conditions:String,

  price:String

)

object Lot {
  def fromJson(obj:JsValue) : Lot = {
    new Lot(
      (obj \ "id").as[String],
      Location.fromJson(obj \ "location"),
      (obj \ "rooms").as[Int],
      (obj \ "level").as[Int],
      (obj \ "square").as[Int],
      (obj \\ "spaces").seq.collect{
        case s : JsObject => Space.fromJson(s)
      }.toList,
      (obj \ "pets").as[String],
      (obj \ "manageCo").as[String],
      (obj \ "communications").as[String],
      (obj \ "counters").as[String],
      (obj \ "phone").as[String],
      (obj \ "sub_conditions").as[String],
      (obj \ "price").as[String]
    )
  }

  def toJson(obj:Lot) : JsValue = {
    JsObject(Seq(
      "id" -> Json.toJson(obj.id),
      "location" -> Location.toJson(obj.location),
      "rooms" -> Json.toJson(obj.rooms),
      "level" -> Json.toJson(obj.level),
      "square" -> Json.toJson(obj.square),
      "spaces" -> JsArray(obj.spaces.collect {
        case space: Space => Space.toJson(space)
      }.toSeq),
      "pets" -> Json.toJson(obj.pets),
      "manageCo" -> Json.toJson(obj.manageCo),
      "communications" -> Json.toJson(obj.communications),
      "counters" -> Json.toJson(obj.counters),
      "phone" -> Json.toJson(obj.phone),
      "sub_conditions" -> Json.toJson(obj.sub_conditions),
      "price" -> Json.toJson(obj.price)
    ))
  }

  def empty: Lot = {
    new Lot(
      UUID.randomUUID().toString,
      new Location("", "", new Address("a", "b", "c", "d")),
      1, 2, 3,
      List(new Space("h", "j")),
      "true",
      "q", "w", "e", "r", "t", "y"
    )
  }

  def store(obj:Lot) = {
    val path = "./lot/" + obj.id + "/info.json"
    val jsonObject = toJson(obj)
    val text = Json.stringify(jsonObject)
    if (new java.io.File(path).exists)
      new java.io.File(path).delete
    Resource.fromFile(path).write(text)

  }

  def all : List[Lot] = {

    val lot_list: List[String] = new File(new java.io.File("./lot")).toDirectory.dirs.collect[String] {
      case d: Directory => "./lot/" + d.name + "/info.json"
    }.toList

    lot_list.collect {
      case path: String =>
        val jsLot = Json.parse(Source.fromFile(path).mkString)
        fromJson(jsLot)
    }.toList

  }

  def byId(id:String) : Lot = {

    val file = Source.fromFile("./lot/" + id + "/info.json").mkString
    if (file.isEmpty) null
    else fromJson(Json.parse(file))

  }

}
