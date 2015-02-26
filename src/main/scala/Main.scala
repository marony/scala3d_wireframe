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

object Main  extends SimpleSwingApplication {
  def top = new MainFrame {
    // Windowのタイトル
    title = "Window Title"
    contents = new Panel {
//      val polygons = Util.objToPolygons("teapot.obj")
      val polygons = Util.objToPolygons("human.obj")
//      val polygons = Array[(Polygon3, Color)](
//        (Polygon3(Point3(1, 1, 1), Point3(0, 0, 1), Point3(0, 1, 1)), new Color(255, 0, 0)),
//        (Polygon3(Point3(1, 1, 1), Point3(0, 0, 1), Point3(1, 0, 1)), new Color(0, 0, 255))
//      )
      println(polygons.length)
      // Windowのサイズ
      val world = World(polygons)
      preferredSize = new Dimension(world.WIDTH, world.HEIGHT)

      def timer = new Timer(50, new ActionListener {
        def actionPerformed(e: ActionEvent) {
          world.rotate = world.rotate + 3
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
