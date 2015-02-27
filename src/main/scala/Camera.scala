/**
 * Created with IntelliJ IDEA.
 * User: tada
 * Date: 13/05/19
 * Time: 21:51
 * To change this template use File | Settings | File Templates.
 */
case class Camera(position : Point3, lookAt : Point3, up : Vector3, near : Double, far : Double) {
  def direction : Vector3 = lookAt - position

  def isCull(polygon : Polygon3) : Boolean = polygon.normal <*> direction >= 0

  // ビューポート変換
  def convertToView(polygon : Polygon3) : Polygon3 = {
    val z = direction.normalize
    val x = (up * z).normalize
    val y = (z * x).normalize
    val tx = position <*> x * -1
    val ty = position <*> y * -1
    val tz = position <*> z * -1
//    println("direction:" + direction + ", x:" + x + ", y:" + y + ", z:" + z + ", tx:" + tx + ", ty:" + ty + ", tz:" + tz)
    val matrix = Matrix4(
      x.x, x.y, x.z, tx,
      y.x, y.y, y.z, ty,
      z.x, z.y, z.z, tz,
      0, 0, 0, 1
    )
    polygon.affin(matrix)
  }

  // 投影変換
  def projection(point : Point3, screen : Screen) : Point3 = {
    Point3(
      near * 2 * point.x / screen.size.width,
      near * 2 * point.y / screen.size.height,
      (far + near) * point.z / (far - near) + (2 * near * far) / (far - near),
      (2 * near * far) * point.w / (far - near)
    )
  }

  // 投影変換
  def projection(polygon : Polygon3, screen : Screen) : Polygon3 = {
    Polygon3(
      projection(polygon.p1, screen),
      projection(polygon.p2, screen),
      projection(polygon.p3, screen)
    )
  }

  def perspective(polygon : Polygon3) : Polygon3 = {
    Polygon3(polygon.p1 / polygon.p1.w, polygon.p2 / polygon.p2.w, polygon.p3 / polygon.p3.w)
  }
}
