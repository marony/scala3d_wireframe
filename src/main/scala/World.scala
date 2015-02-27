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
  val SCALE = 2000
//  val SCALE = 30000
//  val SCALE = 80000

  // スクリーン(画面)
  val screen = Screen(Size(WIDTH, HEIGHT), SCALE)
  // カメラ
  val camera = Camera(Point3(0, 0, -100), Vector3(0, 0, 0), Vector3(0, 1, 0), 10, 200)
//  val camera = Camera(Point3(-2, 2, -5), Vector3(2, -3, 0), Vector3(0, 1, 0), 10.0, 100.0)
//  val camera = Camera(Point3(0, 0, -2), Vector3(0, 0, 0), Vector3(0, 1, 0), 0.5, 2.0)
  // 光源
  val light = Light(Point3(-500, 500, -500))

  def draw(g : Graphics2D) : Unit = {
    val color = new Color(255, 255, 255)
    g.setColor(color)
    // ポリゴン群を投影面の座標に変換
    rotate = rotate % 360
    polygons.
      map { case (p, c) => (p.rotateX(30d / 360 * 2 * Pi).
        // 回転
        rotateY(rotate.asInstanceOf[Double] / 360 * 2 * Pi), c) }.
      // 拡散光の計算
      map { case (p, c) => (p, light.getDiffuseColor(c, p)) }.
      // カリング(カメラから見て裏面のポリゴンは省略)
      filter { case (p, c) => !camera.isCull(p) }.
      // ビューポート変換
      map { case (p, c) => /*println("convertToView:" + p);*/ (camera.convertToView(p), c)}.
      // 奥からソート
      sortBy((pc) => -1.0 * (pc._1.p1.z + pc._1.p2.z + pc._1.p3.z)).
      // 射影変換
      map { case (p, c) => /*println("projection:" + p);*/ (camera.projection(p, screen), c)}.
      // 遠近感
      filter { case (p, c) =>
        p.p1.z >= camera.near && p.p1.z <= camera.far &&
        p.p2.z >= camera.near && p.p2.z <= camera.far &&
        p.p3.z >= camera.near && p.p3.z <= camera.far}.
      map { case (p, c) => /*println("perspective:" + p);*/ (camera.perspective(p), c)}.
      // スクリーン変換
      map { case (p, c) => (screen.convertToScreen(p), c)}.
      // 描画
      foreach { case (p, c) => {
        p.draw(g, c)
      }}
  }
}
