package models

import play.api.libs.json.{Json, JsObject, JsValue}

/**
 * Created with IntelliJ IDEA.
 * @author <a href="mailto:wstarcev@gmail.com">Vasilii Startsev</a>
 *         Date: 28.08.13
 *         Time: 6:03
 */
case class Option(name: String, value: Boolean)

object Option {
  def fromJson(obj: JsValue): Option =
    Option(
      (obj \ "name").as[String],
      (obj \ "value").as[Boolean]
    )

  def toJson(obj: Option): JsValue =
    JsObject(Seq(
      "name" -> Json.toJson(obj.name),
      "value" -> Json.toJson(obj.value)
    ))
}
