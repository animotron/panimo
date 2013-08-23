package controllers

import play.api._
import play.api.mvc._
import models.NeoStart
import models.Position

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
}