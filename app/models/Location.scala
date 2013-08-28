package models

import play.api.libs.json.{Json, JsObject, JsValue}
import play.api.libs.json.Json._

/**
 * Created with IntelliJ IDEA.
 * @author <a href="mailto:wstarcev@gmail.com">Vasilii Startsev</a>
 *         Date: 28.08.13
 *         Time: 6:01
 */

case class Location(latitude: String, longitude: String, address: Address)

object Location {
  def fromJson(obj: JsValue): Location = {
    new Location(
      (obj \ "latitude").as[String],
      (obj \ "longitude").as[String],
      Address.fromJson(obj \ "address")
    )
  }

  def toJson(obj: Location): JsValue = {
    JsObject(Seq(
      "latitude" -> Json.toJson(obj.latitude),
      "longitude" -> Json.toJson(obj.longitude),
      "address" -> Address.toJson(obj.address)
    ))
  }
}
