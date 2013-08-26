package controllers

import play.api._
import play.api.mvc._
import scala.reflect.io.{Directory, File}
import play.api.data._
import play.api.data.Forms._
import models.Lot
import play.api.libs.json.Json

/**
 * Created with IntelliJ IDEA.
 * @author <a href="mailto:wstarcev@gmail.com">Vasilii Startsev</a>
 *         Date: 23.08.13
 *         Time: 23:21
 */
object Adminka extends Controller {

  val lotCreateForm = Form(
    mapping(
      "name" -> optional(text),
      "rooms" -> optional(number),
      "level" -> optional(number),
      "description" -> optional(text)
    )(Lot.apply)(Lot.unapply)
  )


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

    Ok(views.html.adminka.index(list, lotCreateForm))
  }

  def add_new = Action { implicit request =>

    lotCreateForm.bindFromRequest.fold(
    errors => NotFound,
    lot => println(lot.description)
    )

    Ok
  }
}
