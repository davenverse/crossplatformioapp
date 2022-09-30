# CrossPlatformIOApp - Cross Platform IOApp Instantiation [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.chrisdavenport/CrossPlatformIOApp_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.chrisdavenport/CrossPlatformIOApp_2.13) ![Code of Conduct](https://img.shields.io/badge/Code%20of%20Conduct-Scala-blue.svg)

## Quick Start

To use CrossPlatformIOApp in an existing SBT project with Scala 2.13 or a later version, add the following dependencies to your
`build.sbt` depending on your needs:

```scala
libraryDependencies ++= Seq(
  "io.chrisdavenport" %% "crossplatformioapp" % "<version>"
)
```

Use is super straightforward, use exactly like IOApp/IOApp.Simple but automatically get working app for scala-native with epollcat.

```scala
import cats.effect._
import io.chrisdavenport.crossplatformioapp.CrossPlatformIOApp

object Main extends CrossPlatformIOApp.Simple {
  def run: IO[Unit] = IO.println("Hello world!")
}
```