/**
 * Created with IntelliJ IDEA.
 * User: tada
 * Date: 13/05/18
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */

import java.awt.event.{ActionEvent, ActionListener, WindowEvent, WindowAdapter}
import java.awt.{Color, Graphics2D}
import javax.swing.Timer
import scala.swing.{Panel, SimpleSwingApplication, MainFrame, Dimension}
import scala.io.Source
import scala.util.matching.Regex

object Main  extends SimpleSwingApplication {
  def top = new MainFrame {
    // Windowのタイトル
    title = "Window Title"
    contents = new Panel {
      // 初期データ(objファイル)読み込み
      // 頂点のデータ
      val regex1 = """^v +([-0-9.]+) +([-0-9.]+) +([-0-9.]+)""".r
      val points = Source.fromFile("teapot.obj").getLines.
        filter((l) => regex1.findFirstIn(l).nonEmpty).
        map { case regex1(x, y, z) => Point3(x.toDouble, y.toDouble, z.toDouble) }.
        toArray
      println("points.length = " + points.length) //7850
      // ポリゴンのデータ
      val regex2 = """^f +([0-9]+)/[0-9]+ +([0-9]+)/[0-9]+ +([0-9]+)/[0-9]+ +([0-9]+)/[0-9]+ *""".r
      val regex3 = """^f +([0-9]+)/[0-9]+ +([0-9]+)/[0-9]+ +([0-9]+)/[0-9]+ *""".r
      val polygons = Source.fromFile("teapot.obj").getLines.
        filter((l) => regex2.findFirstIn(l).nonEmpty || regex3.findFirstIn(l).nonEmpty).
        flatMap { (l) =>
          l match {
            case regex2(a, b, c, d) => {
              Seq(
                Polygon3(points(a.toInt - 1), points(b.toInt - 1), points(c.toInt - 1)),
                Polygon3(points(a.toInt - 1), points(c.toInt - 1), points(d.toInt - 1))
              )
            }
            case regex3(a, b, c) => {
              Seq(Polygon3(points(a.toInt - 1), points(b.toInt - 1), points(c.toInt - 1)))
            }
          }
        }.
        toArray
      println("polygons.length = " + polygons.length)
      // Windowのサイズ
      val world = World(polygons)
      preferredSize = new Dimension(world.WIDTH, world.HEIGHT)

      def timer = new Timer(200, new ActionListener {
        def actionPerformed(e: ActionEvent) {
          world.rotate = world.rotate + 10
          repaint()
        }
      })

      timer.start()

      override def paint(g : Graphics2D) {
        val color = new Color(0, 0, 0)
        g.setColor(color)
        g.fillRect(0, 0, size.width, size.height)
        world.draw(g)
      }
    }
  }
}
