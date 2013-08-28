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

  pets:Boolean,

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
      (obj \\ "spaces").map(Space.fromJson).toList,
      (obj \ "pets").as[Boolean],
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
      "id" -> toJson(obj),
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
      new Location("", "", new Address("", "", "", "")),
      0, 0, 0,
      List.empty[Space],
      false,
      "", "", "", "", "", ""
    )
  }

  val webForm = Form(
    mapping(
      "id" -> text,
      "location" -> mapping(
        "latitude" -> text,
        "longitude" -> text,
        "address" -> mapping(
          "city" -> text,
          "street" -> text,
          "metro" -> text,
          "home" -> text
        )(Address.apply)(Address.unapply)
      )(Location.apply)(Location.unapply),
      "rooms" -> number,
      "level" -> number,
      "square" -> number,
      "spaces" -> list(mapping(
        "name" -> text,
        "s" -> text
      )(Space.apply)(Space.unapply)),
      "pets" -> checked("pets"),
      "manageCo" -> text,
      "communications" -> text,
      "counters" -> text,
      "phone" -> text,
      "sub_conditions" -> text,
      "price" -> text
    )(Lot.apply)(Lot.unapply)
  )

  def store(obj:Lot) = {
    val jsonObject = toJson(obj)
    Resource.fromFile("./lot/" + obj.id + "/info.json").write(Json.stringify(jsonObject))

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
