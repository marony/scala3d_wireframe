/**
 * Created by tada on 2015/02/04.
 */

import scala.io.Source
import scala.util.matching.Regex

object Util {
  def objToPolygons(fileName : String) : Array[Polygon3] = {
    // 初期データ(objファイル)読み込み
    // 頂点のデータ
    val regex1 = """^v +([-0-9.]+) +([-0-9.]+) +([-0-9.]+)""".r
    val points = Source.fromFile(fileName).getLines.
      filter((l) => regex1.findFirstIn(l).nonEmpty).
      map { case regex1(x, y, z) => Point3(x.toDouble, y.toDouble, z.toDouble) }.
      toArray
    // ポリゴンのデータ
    val regex2 = """^f +([0-9]+)/[0-9]+ +([0-9]+)/[0-9]+ +([0-9]+)/[0-9]+ +([0-9]+)/[0-9]+ *""".r
    val regex3 = """^f +([0-9]+)/[0-9]+ +([0-9]+)/[0-9]+ +([0-9]+)/[0-9]+ *""".r
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
      polygons.foldLeft(0.0)((acc, p) => acc + p.p1.x + p.p2.x + p.p3.x) / polygons.length,
      polygons.foldLeft(0.0)((acc, p) => acc + p.p1.y + p.p2.y + p.p3.y) / polygons.length,
      polygons.foldLeft(0.0)((acc, p) => acc + p.p1.z + p.p2.z + p.p3.z) / polygons.length)
    polygons.map((p) => p.move((Vector3(0, 0, 0) - aveP) / 2.0))
  }
}
