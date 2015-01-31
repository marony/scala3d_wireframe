/**
 * Created with IntelliJ IDEA.
 * User: tada
 * Date: 13/05/19
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */

import java.awt.{Color, Graphics2D}
import scala.math.{Pi}

case class World() {
  var rotate : Int = 0

  val WIDTH = 640
  val HEIGHT = 480
  val SCALE = 80.0

  // カメラ
  val camera = Camera(Point3(0, 0, 5), Vector3(0, 0, 1), 10)
  // 光源
  val light = Light(Point3(-5, 3, -10))

  // 物体を定義するポリゴン
  val polygons = List(
    // ↓のモデルをそのまま頂いた
    // http://codezine.jp/article/detail/38
    // 0 p1, p4, p2
    Polygon3(Point3(0, 1, 0), Point3(0, 0, 1), Point3(0, 0, -1)),
    // 3 p2, p4, p3
    Polygon3(Point3(0, 0, -1), Point3(0, 0, 1), Point3(1, 0, 0)),
    // 5 p4, p5, p3
    Polygon3(Point3(0, 0, 1), Point3(0, -1, 0), Point3(1, 0, 0)),
    // 6 p3, p5, p2
    Polygon3(Point3(1, 0, 0), Point3(0, -1, 0), Point3(0, 0, -1)),
    // 4 p0, p5, p4
    Polygon3(Point3(-1, 0, 0), Point3(0, -1, 0), Point3(0, 0, 1)),
    // 7 p5, p0, p2
    Polygon3(Point3(0, -1, 0), Point3(-1, 0, 0), Point3(0, 0, -1)),
    // 1 p1, p0, p4
    Polygon3(Point3(0, 1, 0), Point3(-1, 0, 0), Point3(0, 0, 1)),
    // 2 p1, p2, p0
    Polygon3(Point3(0, 1, 0), Point3(0, 0, -1), Point3(-1, 0, 0)),
    // 4 p0, p5, p4
    Polygon3(Point3(-1, 0, 0), Point3(0, -1, 0), Point3(0, 0, 1))
  )

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
    // TODO: 投影おかしい？
    // カメラの位置の逆に移動
    convertToViewPort(Point3(
      (point.x - camera.position.x) / ((1.0 - point.z - camera.position.z) / camera.distance),
      (point.y - camera.position.y) / ((1.0 - point.z - camera.position.z) / camera.distance),
      camera.distance
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
        map(_.rotateX(20d / 360 * 2 * Pi).
              // 回転
              rotateY(rotate.asInstanceOf[Double] / 360 * 2 * Pi)).
      // 隠面消去
      filter(_.normal <*> camera.direction <= 0).
      // 奥からソート
      sortBy((p) => -1.0 * (p.p1.z + p.p2.z + p.p3.z)).
      // 拡散光の計算
      map((p) => (p, light.getColor(new Color(0, 80, 255), p))).
      // ビューに合わせる
      map { case (p, c) => (convertToView(p), c)}.
      // 描画
      foreach { case (p, c) => {
        p.draw(g, c)
      }}
  }
}
