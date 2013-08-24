package controllers

import play.api._
import play.api.mvc._
import models.NeoStart
import models.Position
import scala.reflect.io.{Directory, File}
import play.api.libs.json.{Json, JsArray, JsValue, JsObject}
import play.api.libs.json.Json.JsValueWrapper

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready.", Position.all))
  }

  def users = Action {
    NeoStart.increase
    Ok(views.html.users(NeoStart.mainName))
  }

  def playHelp = Action {
    Ok(views.html.p_help())
  }

  def lot(id: String) = Action {
    Ok(views.html.lot(id))

  }

  def res(id:String, file:String) = Action { response =>
    val path = "./lot/" + id + "/" + file
    println(path)
    if (path.endsWith(".xml"))
      Ok(scala.io.Source.fromFile(path).mkString).withHeaders(
        ("Content-Type", "text/xml")
      )
    else {
      try {
        val file_b = play.Play.application.getFile(path)
        val source = scala.io.Source.fromFile(file_b)(scala.io.Codec.ISO8859)
        val byteArray = source.map(_.toByte).toArray
        source.close()

        Ok(byteArray).as("image/jpeg")
      } catch {
        case e: Exception => {
          println(e.getMessage)
          Ok(e.getMessage)
        }
      }

    }
  }

  def allPoints = Action {
    val lot_list: List[String] = new File(new java.io.File("./lot")).toDirectory.dirs.collect[String] {
      case d: Directory => "./lot/" + d.name + "/info.json"
    }.toList

    val main = Json.obj("list" -> Json.toJson(
      lot_list.collect{
        case path : String =>
          Json.parse(scala.io.Source.fromFile(path).mkString)
      }
    ))

    Ok(Json.stringify(main))
  }
}