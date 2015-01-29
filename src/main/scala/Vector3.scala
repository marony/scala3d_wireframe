/**
 * Created with IntelliJ IDEA.
 * User: tada
 * Date: 13/05/19
 * Time: 21:52
 * To change this template use File | Settings | File Templates.
 */

import scala.math.sqrt

object Vector3 {
  implicit def vector3ToPoint3(vector : Vector3) : Point3 = {
    Point3(vector.x, vector.y, vector.z)
  }
}

case class Vector3(x : Double, y : Double, z : Double) {
  def length = {
    sqrt(x * x + y * y + z * z)
  }
  // 単位ベクトルを取得
  def toUnitVector = {
    Vector3(x / length, y / length, z / length)
  }
  // 加算
  def +(vector : Vector3) = {
    Vector3(x + vector.x, y + vector.y, z + vector.z)
  }
  // 減算
  def -(vector : Vector3) = {
    Vector3(x - vector.x, y - vector.y, z - vector.z)
  }
  // 乗算
  def *(a : Double) = {
    Vector3(x * a, y * a, z * z)
  }
  // 除算
  def /(a : Double) = {
    Vector3(x / a, y / a, z / z)
  }
  // 内積
  def <*>(vector : Vector3) : Double = {
    x * vector.x + y * vector.y + z * vector.z
  }
  // 外積
  def *(vector : Vector3) = {
    Vector3(y * vector.z - z * vector.y, z * vector.x - x * vector.z, x * vector.y - y * vector.x)
  }
}
