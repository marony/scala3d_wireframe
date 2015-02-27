# Scala + Swingで3D表示のテスト

　ScalaでSwingと3Dのロジックのテストです。

![動画](/resources/Video_2015-02-01_100051.gif)

![静止画](/resources/2015-02-01_095951.png)

# 仕様

## 座標系

- 右手座標系が主流なんて知らなかったので、左手座標系
  - x軸は左から右
  - y軸は下から上
  - z軸は手前から奥
- ポリゴンは半時計回りが表(怪しい)

## 処理の流れ

- オブジェクトの回転
- 拡散光の計算
- ビューポート変換
- カリング
- 奥からソート
- 射影変換
- スクリーン変換
- 描画

## クラス

### World

世界だぜ

### Screen

画面

- スクリーン変換

### Camera

カメラ

- カメラの位置・注視点・上向きベクトル
- ビューポート変換
- 射影変換

### Light

ライト
今は無限平行光源

- 拡散光の計算

### その他のクラス

雑多なユーティリティ系

- Polygon3
- Point3
- Vector3
- Matrix4
- Size
- Util

## 参考URL

- [3D、カメラ周りの勉強中なので、その情報集め。あと理解したもののメモ - Qiita](http://qiita.com/edo_m18/items/946df01d58b8cd3143d4)
- [3Dプログラミング基礎知識(3) | Tech-Sketch](http://tech-sketch.jp/2011/11/3d3.html)
- [カリング - ゲームプログラミングWiki](http://www.c3.club.kyutech.ac.jp/gamewiki/index.php?%A5%AB%A5%EA%A5%F3%A5%B0)
- [Canvas context 2Dで自作3Dエンジンを作る - jsdo.it - Share JavaScript, HTML5 and CSS](http://jsdo.it/edo_m18/9Xku)
