/**
 * Created by tada on 2015/01/29.
 */
import java.awt.Color

case class Light(position : Point3) {
  def getColor(color : Color, polygon3: Polygon3): Color = {
    // 拡散光の計算(ランバードの余弦則)
    // TODO: 計算おかしい？
    val L = (position - polygon3.p1).toUnitVector
    val cosa = L <*> polygon3.normal.toUnitVector * -1.0
    println(s"L = $L, cosa = $cosa, position = $position, polygon3 = $polygon3")
    var R = (color.getRed * cosa * 0.8 + 0.2).asInstanceOf[Int]
    var G = (color.getGreen * cosa * 0.8 + 0.2).asInstanceOf[Int]
    var B = (color.getBlue * cosa * 0.8 + 0.2).asInstanceOf[Int]
    R = Math.min(255, Math.max(0, R))
    G = Math.min(255, Math.max(0, G))
    B = Math.min(255, Math.max(0, B))
    println(s"color = $color, R = $R, G = $G, B = $B")
    new Color(R, G, B)
  }
}
