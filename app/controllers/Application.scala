package controllers

import play.api.mvc._
import models.{Lot, NeoStart}
import play.api.libs.json.{Json, JsArray}

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Welcome to Infostark"))
  }

  def search = Action {
    Ok(views.html.lot.search("Infostark", models.Lot.all))
  }

  def users = Action {
    NeoStart.increase
    Ok(views.html.users(NeoStart.mainName))
  }
}