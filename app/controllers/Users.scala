package controllers

import play.api.mvc.{Action, Controller}
import play.libs.Json

/**
 * Created with IntelliJ IDEA.
 * @author <a href="mailto:wstarcev@gmail.com">Vasilii Startsev</a>
 *         Date: 13.08.13
 *         Time: 23:13
 */
object Users extends Controller{

  def index = Action {
    Ok
  }

  def home = Action {
    Ok
  }

  def login = Action(parse.json) { request =>
    val json = request.body
    System.out.println("User: " + json \\ "user")
    System.out.println("Pass: " + json \\ "pass")
    System.out.println("JSON " + request.body)
    Ok
  }

  def register = Action(parse.json) { request =>
    val json = request.body
    System.out.println("User: " + json \\ "user" )
    System.out.println("Pass: " + json \\ "pass")
    System.out.println("JSON " + request.body.\("register"))
    Ok
  }

}
