package controllers

import play.api._
import play.api.mvc._
import scala.reflect.io.{Directory, File}

/**
 * Created with IntelliJ IDEA.
 * @author <a href="mailto:wstarcev@gmail.com">Vasilii Startsev</a>
 *         Date: 23.08.13
 *         Time: 23:21
 */
object Adminka extends Controller {
  def index = Action {
    val list = new File(new java.io.File("./lot")).toDirectory.dirs.collect[String]{ case d : Directory => d.name }.toList
    Ok(views.html.adminka.index(list))
  }

  def add_new = Action {
    Ok
  }
}
