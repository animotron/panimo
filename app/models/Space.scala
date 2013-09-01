package models

import play.api.libs.json.{Json, JsObject, JsValue}

/**
 * Created with IntelliJ IDEA.
 * @author <a href="mailto:wstarcev@gmail.com">Vasilii Startsev</a>
 *         Date: 28.08.13
 *         Time: 6:02
 */

case class Space(name: String, square: String)

object Space {
  def fromJson(obj: JsValue): Space =
    Space(
      (obj \ "name").as[String],
      (obj \ "square").as[String]
    )

  def toJson(obj: Space): JsValue =
    JsObject(Seq(
      "name" -> Json.toJson(obj.name),
      "square" -> Json.toJson(obj.square)
    ))
}
