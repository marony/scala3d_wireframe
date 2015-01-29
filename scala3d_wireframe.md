# Scalaプロジェクトの作成
#＃ディレクトリの作成
    $ scala3d_wireframe
    $ cd scala3d_wireframe
    $ mkdir src
    $ mkdir src\main
    $ mkdir src\main\java
    $ mkdir src\main\resources
    $ mkdir src\main\scala
    $ mkdir src\test
    $ mkdir src\test\java
    $ mkdir src\test\resources
    $ mkdir src\test\scala

## sbtファイルの作成
build.sbt    

    name := "scala3d_wireframe"

    version := "1.0"

    scalaVersion := "2.11.5"

    libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.11" % "1.0.1"

## 実行
    $ sbt run

## IntelliJ IDEAでSBTをインポート

    

## SBTコンソールから実行
    sbt run
