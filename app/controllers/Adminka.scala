package controllers

import play.api._
import play.api.mvc._
import scala.reflect.io.{Directory, File}
import play.api.data._
import play.api.data.Forms._
import models.{Space, Location, Address, Lot}
import play.api.libs.json.Json
import java.util.UUID

/**
 * Created with IntelliJ IDEA.
 * @author <a href="mailto:wstarcev@gmail.com">Vasilii Startsev</a>
 *         Date: 23.08.13
 *         Time: 23:21
 */
object Adminka extends Controller {

  def index = Action {
    val list = new File(new java.io.File("./lot")).toDirectory.dirs.collect[String]{ case d : Directory => d.name }.toList

    val lot_list: List[String] = new File(new java.io.File("./lot")).toDirectory.dirs.collect[String] {
      case d: Directory => "./lot/" + d.name + "/info.json"
    }.toList

    val main = Json.obj("list" -> Json.toJson(
      lot_list.collect {
        case path: String =>
          Json.parse(scala.io.Source.fromFile(path).mkString)
      }
    ))
    Ok(views.html.adminka.index(list, Lot.webForm.fill(Lot.empty)))
  }

  def add_new = Action { implicit request =>

    Lot.webForm.bindFromRequest.fold(
      errors => NotFound,
      success => {
        Lot.store(success)
      }
    )
    Redirect("/admin/", 200)
//    Ok(TODO.apply(request))
  }
}
