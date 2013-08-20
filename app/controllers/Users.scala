package controllers

import play.api.mvc.{WebSocket, Action, Controller}
import play.libs.Json
import play.api.libs.iteratee.{Enumerator, Iteratee}
import play.api.libs.concurrent.Execution.Implicits._

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
    println(request)

    // Send a single 'Hello!' message and close
    val out = Enumerator("Hello!", "ohloh")

    val in = Iteratee.foreach[String] { json => {
      println(json)
      println("Ha!")
    }}

    (in, out)

  }

}
