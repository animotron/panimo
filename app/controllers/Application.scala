package controllers

import play.api._
import play.api.mvc._
import org.neo4j.kernel._
import models.NeoStart

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def users = Action {
    NeoStart.increase()
    Ok(views.html.users(NeoStart.mainName))
  }

  def playHelp = Action {
    Ok(views.html.p_help())
  }

}