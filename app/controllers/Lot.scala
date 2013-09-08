package controllers

import java.io.{BufferedOutputStream, BufferedInputStream, File, FileOutputStream}
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.{Lot, Space, Location, Address}
import org.apache.commons.compress.archivers.zip.ZipFile
import play.api.libs.json.{JsArray, Json}

/**
 * Created with IntelliJ IDEA.
 * @author <a href="mailto:wstarcev@gmail.com">Vasilii Startsev</a>
 *         Date: 23.08.13
 *         Time: 23:21
 */
object Lot extends Controller {

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
          "house" -> text
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
    )(models.Lot.apply)(models.Lot.unapply)
  )

  def lot(id: String) = Action {
    val info = Json.parse(scala.io.Source.fromFile("./lot/" + id + "/info.json").mkString)
    Ok(views.html.lot.lot(models.Lot.fromJson(info)))
  }

  def lots = Action {
    val lots = models.Lot.all
    val main = Json.obj("list" -> JsArray(
      lots.collect {
        case lot : Lot => models.Lot.toJson(lot)
      }.toSeq
    ))
    Ok(Json.stringify(main))
  }

  def add = Action {implicit request =>
    val data = webForm.bindFromRequest
    data.fold(
      errors => println(errors.errorsAsJson),
      success => models.Lot.store(success)
    )
    Redirect("/admin/")
  }

  def edit(id: String) = Action {
    Ok(views.html.lot.edit(id, webForm.fill(models.Lot.byId(id))))
  }

  def store(id: String) = Action {
    Ok(views.html.lot.edit(id, webForm.fill(models.Lot.byId(id))))
  }

  def upload(id: String) = Action(parse.multipartFormData) {
    request =>
      request.body.file("zip").map {
        zip =>
          val trg = new File(s"lot/$id")
          trg.mkdir()
          val img = new File(trg, "images")
          if (img.exists())
            for (f <- img.listFiles) yield f.delete
          else img.mkdir()
          val out = new File(trg, "out.xml")
          val z = new ZipFile(new File(zip.ref.file.getPath))
          val entries = z.getEntries
          while (entries.hasMoreElements) {
            val entry = entries.nextElement()
            val name = new File(entry.getName).getName
            def store (file: File) = {
              val is = new BufferedInputStream(z.getInputStream(entry))
              val os = new BufferedOutputStream(new FileOutputStream(file))
              Iterator
                .continually (is.read)
                .takeWhile (-1 !=)
                .foreach (os.write)
              os.close()
              is.close()
            }
            if (name.equals("out.xml")) store(out)
            else if (name.endsWith(".jpg")) store(new File(img, name))
          }
      }
      Ok("")
  }
}
