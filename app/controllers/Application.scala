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
    val info = Json.parse(scala.io.Source.fromFile("./lot/" + id + "/info.json").mkString)
    Ok(views.html.lot(AnyContentAsJson(info)))
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