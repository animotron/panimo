package controllers

import play.api.mvc.{WebSocket, Action, Controller}
import play.libs.Json
import play.api.libs.iteratee.{Step, Enumerator, Iteratee}
import play.api.libs.json.JsValue
import play.api.libs.concurrent.Promise
import scala.parallel.Future

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

  def ws = WebSocket.using[String] { request =>

  // Just consume and ignore the input
    val in = Iteratee.consume[String]()

    // Send a single 'Hello!' message and close
    val out = Enumerator("Hello!").andThen(Enumerator.eof)

    (in, out)

  }

}
