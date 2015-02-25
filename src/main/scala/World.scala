/**
 * Created with IntelliJ IDEA.
 * User: tada
 * Date: 13/05/19
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */

import java.awt.{Color, Graphics2D}
import scala.math.{Pi}

case class World(polygons : Array[(Polygon3, Color)]) {
  var rotate : Int = 0

  val WIDTH = 640
  val HEIGHT = 480
  // TODO: 読み込みの際にモデルの大きさを合わせる
  val SCALE = 15
//  val SCALE = 200

  // カメラ
  val camera = Camera(Point3(-30, -10, -30), Vector3(0, 0, 0), Vector3(0, 1, 0))
  // 光源
  val light = Light(Point3(-500, 500, -500))

  def convertToScreen(point : Point3) : Point3 = {
    // 投影面をディスプレイに合わせる
    Point3(
      WIDTH / 2 + point.x * SCALE,
      HEIGHT / 2 + point.y * SCALE,
      point.z
    )
  }

  def convertToScreen(polygon : Polygon3) : Polygon3 = {
    // ポリゴンを投影面の座標に合わせる
    Polygon3(
      convertToScreen(polygon.p1),
      convertToScreen(polygon.p2),
      convertToScreen(polygon.p3)
    )
  }

  def draw(g : Graphics2D) : Unit = {
    val color = new Color(255, 255, 255)
    g.setColor(color)
    // ポリゴン群を投影面の座標に変換
    rotate = rotate % 360
    polygons.
      map { case (p, c) => (p.rotateX(20d / 360 * 2 * Pi).
        // 回転
        rotateY(rotate.asInstanceOf[Double] / 360 * 2 * Pi), c) }.
      // 拡散光の計算
      map { case (p, c) => (p, light.getDiffuseColor(c, p)) }.
      // ビューポート変換
      map { case (p, c) => (camera.convertToView(p), c)}.
      // カリング(カメラから見て裏面のポリゴンは省略)
      filter((pc) => !camera.isCull(pc._1)).
      // 奥からソート
      sortBy((pc) => -1.0 * (pc._1.p1.z + pc._1.p2.z + pc._1.p3.z)).
      // 射影変換
      map { case (p, c) => (camera.projection(p, WIDTH, HEIGHT), c)}.
      // スクリーン変換
      map { case (p, c) => (convertToScreen(p), c)}.
      // 描画
      foreach { case (p, c) => {
        p.draw(g, c)
      }}
  }
}
