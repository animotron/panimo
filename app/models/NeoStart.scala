package models

/**
 * Created with IntelliJ IDEA.
 * @author <a href="mailto:wstarcev@gmail.com">Vasilii Startsev</a>
 *         Date: 13.08.13
 *         Time: 4:42
 */

object NeoStart {

  var count : Int = 0
  def mainName : String = "Main name here " + count

  def increase() : Unit = {
    count = count + 1
  }

}
