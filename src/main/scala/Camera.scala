/**
 * Created with IntelliJ IDEA.
 * User: tada
 * Date: 13/05/19
 * Time: 21:51
 * To change this template use File | Settings | File Templates.
 */
case class Camera(position : Point3, direction : Vector3, distance : Double) {
  // 視点を(xv, yv, zv) = (0, 0, 0)とした左手座標系
  // 投影面までの距離zd = dとする
  // xp / d = x / z, yp / d = y / z
}
