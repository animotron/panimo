package controllers

import play.api._
import play.api.mvc._
import org.neo4j.kernel._

object Application extends Controller {

  var neo4j = new EmbeddedGraphDatabase("data")

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def users = Action {
    Ok(views.html.users())
  }


}