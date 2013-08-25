package controllers

import play.api.mvc.{Action, Controller}
import models.Position

/**
 * Created with IntelliJ IDEA.
 * @author <a href="mailto:wstarcev@gmail.com">Vasilii Startsev</a>
 *         Date: 15.08.13
 *         Time: 23:52
 */
object Front extends Controller {
  def index = Action {
    Ok(views.html.index(""))
  }

  def position(id : String) = Action {
    Ok(views.html.position(Position.byId(id)))
  }
}
