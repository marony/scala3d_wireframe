/**
 * Created by tada on 15/02/26.
 */
case class Screen(size : Size, scale : Double) {
  def convertToScreen(point : Point3) : Point3 = {
    // 投影面をディスプレイに合わせる
    Point3(
      size.width / 2 + point.x * scale,
      size.height / 2 - point.y * scale,
      point.z
    )
  }
  def convertToScreen(polygon : Polygon3) : Polygon3 = {
    // ポリゴンをスクリーン(画面)の座標に合わせる
    Polygon3(
      convertToScreen(polygon.p1),
      convertToScreen(polygon.p2),
      convertToScreen(polygon.p3)
    )
  }
}
