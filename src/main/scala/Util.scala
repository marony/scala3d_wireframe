/**
 * Created by tada on 2015/02/04.
 */

import java.awt.Color

import scala.io.Source
import scala.util.matching.Regex
import scala.math.{Pi}

object Util {
  def objToPolygons(fileName : String) : Array[(Polygon3, Color)] = {
    // 初期データ(objファイル)読み込み
    // 頂点のデータ
    val regex1 = """^v +([-0-9.]+) +([-0-9.]+) +([-0-9.]+)""".r
    val points = Source.fromFile(fileName).getLines.
      filter((l) => regex1.findFirstIn(l).nonEmpty).
      map { case regex1(x, y, z) => Point3(x.toDouble, y.toDouble, z.toDouble) }.
      toArray
    // ポリゴンのデータ
    val regex2 = """^f +([0-9]+)//?[0-9]+ +([0-9]+)//?[0-9]+ +([0-9]+)//?[0-9]+ +([0-9]+)//?[0-9]+ *""".r
    val regex3 = """^f +([0-9]+)//?[0-9]+ +([0-9]+)//?[0-9]+ +([0-9]+)//?[0-9]+ *""".r
    val polygons = Source.fromFile(fileName).getLines.
      filter((l) => regex2.findFirstIn(l).nonEmpty || regex3.findFirstIn(l).nonEmpty).
      flatMap { (l) =>
      l match {
        case regex2(a, b, c, d) => {
          Seq(
            Polygon3(points(a.toInt - 1), points(b.toInt - 1), points(c.toInt - 1)),
            Polygon3(points(a.toInt - 1), points(c.toInt - 1), points(d.toInt - 1))
          )
        }
        case regex3(a, b, c) => {
          Seq(Polygon3(points(a.toInt - 1), points(b.toInt - 1), points(c.toInt - 1)))
        }
      }
    }.toArray
    // 中央に寄せる
    val aveP = Point3(
      polygons.foldLeft(0.0)((acc, p) => acc + (p.p1.x + p.p2.x + p.p3.x) / 3.0) / polygons.length,
      polygons.foldLeft(0.0)((acc, p) => acc + (p.p1.y + p.p2.y + p.p3.y) / 3.0) / polygons.length,
      polygons.foldLeft(0.0)((acc, p) => acc + (p.p1.z + p.p2.z + p.p3.z) / 3.0) / polygons.length)
    // TODO: 大きさを合わせる
    // x < 0 and y < 0 -> 水色
    // x >= 0 and y < 0 -> 紫
    // x < 0 and y >= 0 -> 赤
    // x >= 0 and y >= 0 -> 青
    polygons.map((p) => p.move(Vector3(0, 0, 0) - aveP)).
             map((p) => (p,
                         if (p.p1.x < 0 && p.p1.y < 0) new Color(0, 255, 255)
                         else if (p.p1.x >= 0 && p.p1.y < 0) new Color(255, 0, 255)
                         else if (p.p1.x < 0) new Color(255, 0, 0)
                         else new Color(0, 0, 255)))
  }
}
