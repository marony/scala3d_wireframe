/**
 * Created with IntelliJ IDEA.
 * User: tada
 * Date: 13/05/19
 * Time: 21:54
 * To change this template use File | Settings | File Templates.
 */

import java.awt.Graphics2D
import java.awt.Polygon
import java.awt.Color
import scala.math.{cos, sin}

case class Polygon3(p1 : Point3, p2 : Point3, p3 : Point3) {
  // 描画
  def draw(g : Graphics2D, color : Color) {
    // 描画座標
    // xp = x, yp = y
    val xp1 = p1.x
    val yp1 = p1.y
    val xp2 = p2.x
    val yp2 = p2.y
    val xp3 = p3.x
    val yp3 = p3.y
    // 面
    g.setColor(color)
    g.fill(
      new Polygon(
        Array[Int](p1.x.asInstanceOf[Int], p2.x.asInstanceOf[Int], p3.x.asInstanceOf[Int]),
        Array[Int](p1.y.asInstanceOf[Int], p2.y.asInstanceOf[Int], p3.y.asInstanceOf[Int]), 3))
    // ワイヤー
/*
    g.setColor(Color.white)
    g.drawLine(xp1.asInstanceOf[Int], yp1.asInstanceOf[Int], xp2.asInstanceOf[Int], yp2.asInstanceOf[Int])
    g.drawLine(xp2.asInstanceOf[Int], yp2.asInstanceOf[Int], xp3.asInstanceOf[Int], yp3.asInstanceOf[Int])
    g.drawLine(xp3.asInstanceOf[Int], yp3.asInstanceOf[Int], xp1.asInstanceOf[Int], yp1.asInstanceOf[Int])
*/
  }

  // 法線ベクトル
  def normal : Vector3 = {
    (Point3.point3ToVector3(p2) - p1) * (Point3.point3ToVector3(p3) - p1)
  }

  // 移動
  // | 1 | 0 | 0 | tx |
  // | 0 | 1 | 0 | ty |
  // | 0 | 0 | 1 | tz |
  // | 0 | 0 | 0 | 1  |
  def move(vector : Vector3) : Polygon3 = {
//    Polygon3(p1 + vector, p2 + vector, p3 + vector)
    val matrix = Matrix4(
      1, 0, 0, vector.x,
      0, 1, 0, vector.y,
      0, 0, 1, vector.z,
      0, 0, 0, 1
    )
    Polygon3(matrix * p1, matrix * p2, matrix * p3)
  }
  // 拡大・縮小、反転
  // | sx | 0  | 0  | 0 |
  // | 0  | sy | 0  | 0 |
  // | 0  | 0  | sz | 0 |
  // | 0  | 0  | 0  | 1 |
  def scale(vector : Vector3) : Polygon3 = {
    val matrix = Matrix4(
      vector.x, 0, 0, 0,
      0, vector.y, 0, 0,
      0, 0, vector.z, 0,
      0, 0, 0, 1
    )
    Polygon3(matrix * p1, matrix * p2, matrix * p3)
  }
  // X軸周りに回転
  // | 1 | 0     | 0      | 0 |
  // | 0 | cos r | -sin r | 0 |
  // | 0 | sin r | cos r  | 0 |
  // | 0 | 0     | 0      | 1 |
  def rotateX(r : Double) : Polygon3 = {
    val matrix = Matrix4(
      1, 0, 0, 0,
      0, cos(r), -sin(r), 0,
      0, sin(r), cos(r), 0,
      0, 0, 0, 1
    )
    Polygon3(matrix * p1, matrix * p2, matrix * p3)
  }
  // Y軸周りに回転
  // | cos r  | 0 | sin r | 0 |
  // | 0      | 1 | 0     | 0 |
  // | -sin r | 0 | cos r | 0 |
  // | 0      | 0 | 0     | 1 |
  def rotateY(r : Double) : Polygon3 = {
    val matrix = Matrix4(
      cos(r), 0, sin(r), 0,
      0, 1, 0, 0,
      -sin(r), 0, cos(r), 0,
      0, 0, 0, 1
    )
    Polygon3(matrix * p1, matrix * p2, matrix * p3)
  }
  // Z軸周りに回転
  // | cos r | -sin r | 0 | 0 |
  // | sin r | cos r  | 0 | 0 |
  // | 0     | 0      | 1 | 0 |
  // | 0     | 0      | 0 | 1 |
  def rotateZ(r : Double) : Polygon3 = {
    val matrix = Matrix4(
      cos(r), -sin(r), 0, 0,
      sin(r), cos(r), 0, 0,
      0, 0, 1, 0,
      0, 0, 0, 1
    )
    Polygon3(matrix * p1, matrix * p2, matrix * p3)
  }
}
