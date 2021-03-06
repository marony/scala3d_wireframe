/**
 * Created with IntelliJ IDEA.
 * User: tada
 * Date: 13/05/19
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */

object Point3 {
  implicit def point3ToVector3(point : Point3) : Vector3 = {
    Vector3(point.x, point.y, point.z, point.w)
  }
}

case class Point3(x : Double, y : Double, z : Double, w : Double = 1.0) {
  // 点とベクトルの和
  def +(vector : Vector3) : Point3 = {
    Point3(x + vector.x, y + vector.y, z + vector.z)
  }
  // 点とベクトルの差
  def -(vector : Vector3) : Point3 = {
    Point3(x - vector.x, y - vector.y, z - vector.z)
  }
  // 点と点の差
  def -(point : Point3) : Point3 = {
    Point3(x - point.x, y - point.y, z - point.z)
  }
}
