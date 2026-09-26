package io.chrisdavenport.crossplatformioapp

// epollcat supplied a Native IOApp before cats-effect had one, and never
// published for Scala Native 0.5 because it no longer needed to: cats-effect
// 3.6 brought the event loop into IOApp itself. Native now matches JVM and JS.
private[crossplatformioapp] trait CrossPlatformIOAppPlatform extends cats.effect.IOApp

private[crossplatformioapp] trait CrossPlatformIOAppSimplePlatform extends cats.effect.IOApp.Simple
