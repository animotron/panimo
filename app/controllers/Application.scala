package controllers

import play.api._
import play.api.mvc._
import models.NeoStart
import models.Position
import java.io.{FileReader, File}

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

  def res(id:String, file:String) = Action { response =>
    val path = "./lot/" + id + "/" + file
    println(path)
    if (path.endsWith(".xml"))
      Ok(scala.io.Source.fromFile(path).mkString).withHeaders(
        ("Content-Type", "text/xml")
      )
    else {
      try {
        val file_b = play.Play.application.getFile(path)
        val source = scala.io.Source.fromFile(file_b)(scala.io.Codec.ISO8859)
        val byteArray = source.map(_.toByte).toArray
        source.close()

        Ok(byteArray).as("image/jpeg")
      } catch {
        case e: Exception => {
          println(e.getMessage)
          Ok(e.getMessage)
        }
      }

    }
//      Ok

  }
}