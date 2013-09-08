package controllers

import java.io.{BufferedOutputStream, BufferedInputStream, File, FileOutputStream}
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.{Space, Location, Address, Lot}
import org.apache.commons.compress.archivers.zip.ZipFile

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

  def upload = Action(parse.multipartFormData) {
    request =>
      request.body.file("zip").map {
        zip =>
          val id = request.body.dataParts.get("id").get.head
          val trg = new File(s"lot/$id")
          trg.mkdir()
          val img = new File(trg, "images")
          if (img.exists()) {
            for (f <- img.listFiles) yield f.delete
            img.delete()
          }
          img.mkdir()
          val out = new File(trg, "out.xml")
          val z = new ZipFile(new File(zip.ref.file.getPath), "cp866")
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
          Redirect(routes.Adminka.edit(id))
      }.getOrElse {
        Redirect(routes.Adminka.index).flashing(
          "error" -> "Missing file")
      }
  }
}
