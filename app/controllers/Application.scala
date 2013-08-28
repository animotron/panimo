package controllers

import play.api._
import play.api.mvc._
import models.{Lot, NeoStart}
import play.api.libs.json.{Json, JsArray}

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Welcome to Infostark"))
  }

  def search = Action {
    Ok(views.html.search("Infostark", Lot.all))
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
    val lots = Lot.all

    val main = Json.obj("list" -> JsArray(
      lots.collect{
        case lot : Lot => Lot.toJson(lot)
      }.toSeq
    ))

    Ok(Json.stringify(main))
  }
}