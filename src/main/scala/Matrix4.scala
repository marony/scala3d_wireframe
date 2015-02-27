/**
 * Created with IntelliJ IDEA.
 * User: tada
 * Date: 13/05/20
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */

// 以下の順序で要素を渡す
// |  0 |  1 |  2 |  3 |
// |  4 |  5 |  6 |  7 |
// |  8 |  9 | 10 | 11 |
// | 12 | 13 | 14 | 15 |
case class Matrix4(elements : Double*) {
  // 行列同士の加算
  def +(matrix : Matrix4) = {
    Matrix4(
      elements.zip(elements).map(tuple => tuple._1 + tuple._2) : _*
    )
  }
  // 行列同士の減算
  def -(matrix : Matrix4) = {
    Matrix4(
      elements.zip(elements).map(tuple => tuple._1 - tuple._2) : _*
    )
  }
  // 乗算
  def *(a : Double) = {
    Matrix4(
      elements.map(_ * a) : _*
    )
  }
  // 積
  def *(vector : Vector3) = {
    Vector3(
      elements(0) * vector.x + elements(1) * vector.y + elements(2) * vector.z + elements(3) * vector.w,
      elements(4) * vector.x + elements(5) * vector.y + elements(6) * vector.z + elements(7) * vector.w,
      elements(8) * vector.x + elements(9) * vector.y + elements(10) * vector.z + elements(11) * vector.w,
      elements(12) * vector.x + elements(13) * vector.y + elements(14) * vector.z + elements(15) * vector.w
    )
  }
  // 積
  def *(matrix : Matrix4) = {
    Matrix4(
      (for (y <- (0 to 3)) yield {
        (for (x <- (0 to 3)) yield {
          elements(y * 4 + 0) * matrix.elements(x + 0) +
            elements(y * 4 + 1) * matrix.elements(x + 4) +
            elements(y * 4 + 2) * matrix.elements(x + 8) +
            elements(y * 4 + 3) * matrix.elements(x + 12)
        }).toList
      }).toList.flatten : _*
    )
  }
}
