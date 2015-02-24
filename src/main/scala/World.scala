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
  val SCALE = 10
//  val SCALE = 200

  // カメラ
  // TODO: Z方向以外にカメラを向けられるようにする
  val camera = Camera(Point3(0, -10, -100), Vector3(0, 0, 1), 100)
  // 光源
  val light = Light(Point3(-500, 300, -700))

  def convertToViewPort(point : Point3) : Point3 = {
    // 投影面をディスプレイに合わせる
    Point3(
      WIDTH / 2 + point.x * SCALE,
      HEIGHT / 2 + point.y * SCALE,
      point.z
    )
  }
  def convertToView(point : Point3) : Point3 = {
    // 点を投影面の座標に合わせる
    // x' = x * (z0 / z0 - z) = x / (1 - z / z0)
    // y' = x * (y0 / y0 - z) = y / (1 - y / z0)
    // カメラの位置の逆に移動
//    convertToViewPort(Point3(
//      (point.x - camera.position.x) / ((1.0 - (point.z - camera.position.z)) / camera.distance),
//      (point.y - camera.position.y) / ((1.0 - (point.z - camera.position.z)) / camera.distance),
//      camera.distance
//    ))
//    // http://codezine.jp/article/detail/234?p=2
    convertToViewPort(Point3(
      (camera.distance * 2) / WIDTH * point.x,
      (camera.distance * 2) / HEIGHT * point.y,
      1
    ))
  }

  def convertToView(polygon : Polygon3) : Polygon3 = {
    // ポリゴンを投影面の座標に合わせる
    Polygon3(
      convertToView(polygon.p1),
      convertToView(polygon.p2),
      convertToView(polygon.p3)
    )
  }

  def draw(g : Graphics2D) : Unit = {
    val color = new Color(255, 255, 255)
    g.setColor(color)
    // ポリゴン群を投影面の座標に変換
    rotate = rotate % 360
    polygons.
      map((pc) => (pc._1.rotateX(20d / 360 * 2 * Pi).
              // 回転
        rotateY(rotate.asInstanceOf[Double] / 360 * 2 * Pi), pc._2)).
      // 隠面消去
      filter((pc) => pc._1.normal <*> camera.direction <= 0).
      // 奥からソート
      sortBy((pc) => -1.0 * (pc._1.p1.z + pc._1.p2.z + pc._1.p3.z)).
      // 拡散光の計算
      map((pc) => (pc._1, light.getColor(pc._2, pc._1))).
      // ビューに合わせる
      map { case (p, c) => (convertToView(p), c)}.
      // 描画
      foreach { case (p, c) => {
        p.draw(g, c)
      }}
  }
}
