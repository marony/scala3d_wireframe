/**
 * Created by tada on 2015/01/29.
 */
import java.awt.Color

case class Light(position : Point3) {
  def getDiffuseColor(color : Color, polygon3: Polygon3): Color = {
    // 拡散光の計算(ランバードの余弦則)
    val L = (position - polygon3.p1).normalize
    val cosa = L <*> polygon3.normal.normalize
    var R = (color.getRed * (if (cosa >= 0) cosa * 0.9 else 0.0) + 0.1).asInstanceOf[Int]
    var G = (color.getGreen * (if (cosa >= 0) cosa * 0.9 else 0.0) + 0.1).asInstanceOf[Int]
    var B = (color.getBlue * (if (cosa >= 0) cosa * 0.9 else 0.0) + 0.1).asInstanceOf[Int]
    R = Math.min(255, Math.max((255.0 * 0.1).asInstanceOf[Int], R))
    G = Math.min(255, Math.max((255.0 * 0.1).asInstanceOf[Int], G))
    B = Math.min(255, Math.max((255.0 * 0.1).asInstanceOf[Int], B))
    new Color(R, G, B)
  }
}
