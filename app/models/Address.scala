package models

import play.api.libs.json.{Json, JsObject, JsValue}
import play.api.libs.json.Json._

/**
 * Created with IntelliJ IDEA.
 * @author <a href="mailto:wstarcev@gmail.com">Vasilii Startsev</a>
 *         Date: 28.08.13
 *         Time: 6:00
 */

case class Address(city: String, street: String, metro: String, home: String)

object Address {
  def fromJson(obj: JsValue): Address = {
    new Address(
      (obj \ "city").as[String],
      (obj \ "street").as[String],
      (obj \ "metro").as[String],
      (obj \ "home").as[String]
    )
  }

  def toJson(obj: Address): JsValue = {
    JsObject(Seq(
      "city" -> Json.toJson(obj.city),
      "street" -> Json.toJson(obj.street),
      "metro" -> Json.toJson(obj.metro),
      "home" -> Json.toJson(obj.home)
    ))
  }
}
