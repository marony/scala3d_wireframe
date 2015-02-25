/**
 * Created with IntelliJ IDEA.
 * User: tada
 * Date: 13/05/19
 * Time: 21:51
 * To change this template use File | Settings | File Templates.
 */
case class Camera(position : Point3, lookAt : Point3, up : Vector3) {
  // 視点を(xv, yv, zv) = (0, 0, 0)とした左手座標系

  def direction : Vector3 = position - lookAt

  def isCull(polygon : Polygon3) : Boolean = polygon.normal <*> direction >= 0

  // ビュー変換
  def convertToView(polygon : Polygon3) : Polygon3 = {
    val z = direction.normalize
    val x = (up * z).normalize
    val y = (z * x).normalize
    val matrix = Matrix4(
      x.x, x.y, x.z, position <*> x * -1,
      y.x, y.y, y.z, position <*> y * -1,
      z.x, z.y, z.z, position <*> z * -1,
      0, 0, 0, 1
    )
    polygon.affin(matrix)
  }

  // 投影変換
  def projection(point : Point3, width : Int, height : Int) : Point3 = {
    Point3(
      direction.length * 2 * point.x / width,
      direction.length * 2 * point.y / height,
      1
    )
  }

  // 投影変換
  def projection(polygon : Polygon3, width : Int, height : Int) : Polygon3 = {
    Polygon3(
      projection(polygon.p1, width, height),
      projection(polygon.p2, width, height),
      projection(polygon.p3, width, height)
    )
  }
}
