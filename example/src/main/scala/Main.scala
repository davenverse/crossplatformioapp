package io.chrisdavenport.crossplatformioapp.example

import cats.effect._
import io.chrisdavenport.crossplatformioapp.CrossPlatformIOApp

object Main extends CrossPlatformIOApp.Simple {
  def run: IO[Unit] = IO.println("Hello world!")
}