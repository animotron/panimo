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

  val webForm = Form(
    mapping(
      "id" -> text,
      "location" -> mapping(
        "latitude" -> text,
        "longitude" -> text,
        "address" -> mapping(
          "city" -> text,
          "street" -> text,
          "metro" -> text,
          "home" -> text
        )(Address.apply)(Address.unapply)
      )(Location.apply)(Location.unapply),
      "rooms" -> number,
      "level" -> number,
      "square" -> number,
      "spaces" -> list(mapping(
        "name" -> text,
        "square" -> text
      )(Space.apply)(Space.unapply)),
      "pets" -> text,
      "manageCo" -> text,
      "communications" -> text,
      "counters" -> text,
      "phone" -> text,
      "sub_conditions" -> text,
      "price" -> text
    )(Lot.apply)(Lot.unapply)
  )

  def index = Action {
    Ok(views.html.adminka.index(Lot.all, webForm.fill(Lot.empty)))
  }

  def add = Action {implicit request =>
    val data = webForm.bindFromRequest
    data.fold(
      errors => println(errors.errorsAsJson),
      success => Lot.store(success)
    )
    Redirect("/admin/")
  }

  def edit(id:String) = Action {
    Ok(views.html.adminka.index(Lot.all, webForm.fill(Lot.byId(id))))
  }
}
