ThisBuild / tlBaseVersion := "0.1" // your current series x.y

ThisBuild / organization := "io.chrisdavenport"
ThisBuild / organizationName := "Christopher Davenport"
ThisBuild / licenses := Seq(License.MIT)
ThisBuild / developers := List(
  tlGitHubDev("christopherdavenport", "Christopher Davenport")
)
ThisBuild / tlCiReleaseBranches := Seq("main")
ThisBuild / tlSonatypeUseLegacyHost := true


val Scala213 = "2.13.8"

ThisBuild / crossScalaVersions := Seq("2.12.17", Scala213, "3.3.0")
ThisBuild / scalaVersion := Scala213

ThisBuild / testFrameworks += new TestFramework("munit.Framework")

val catsV = "2.8.0"
val catsEffectV = "3.3.14"
val munitCatsEffectV = "2.0.0-M3"


// Projects
lazy val `CrossPlatformIOApp` = tlCrossRootProject
  .aggregate(core, example)

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .in(file("core"))
  .settings(
    name := "crossplatformioapp",

    libraryDependencies ++= Seq(
      "org.typelevel"               %%% "cats-core"                  % catsV,
      "org.typelevel"               %%% "cats-effect"                % catsEffectV,
      "org.typelevel"               %%% "munit-cats-effect"        % munitCatsEffectV         % Test,
    )
  ).jsSettings(
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule)},
  ).nativeSettings(
    libraryDependencies += "com.armanbilge" %%% "epollcat" % "0.1.1"
  )

val isLinux = Option(System.getProperty("os.name")).exists(_.toLowerCase().contains("linux"))
val isMacOs = Option(System.getProperty("os.name")).exists(_.toLowerCase().contains("mac"))
val isArm = Option(System.getProperty("os.arch")).exists(_.toLowerCase().contains("aarch64"))

lazy val example = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .enablePlugins(NoPublishPlugin)
  .dependsOn(core)
  .in(file("example"))
  .jsSettings(
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule)),
  )
  .nativeSettings(
    nativeConfig ~= { c =>
      if (isLinux) { // brew-installed s2n
        c.withLinkingOptions(c.linkingOptions :+ "-L/home/linuxbrew/.linuxbrew/lib")
      } else if (isMacOs) // brew-installed OpenSSL
        if(isArm) c.withLinkingOptions(c.linkingOptions :+ "-L/opt/homebrew/opt/openssl@3/lib")
        else c.withLinkingOptions(c.linkingOptions :+ "-L/usr/local/opt/openssl@3/lib")
      else c
    },
    envVars ++= {
      val ldLibPath =
        if (isLinux)
          Map("LD_LIBRARY_PATH" -> "/home/linuxbrew/.linuxbrew/lib")
        else Map("LD_LIBRARY_PATH" -> "/usr/local/opt/openssl@1.1/lib")
      Map("S2N_DONT_MLOCK" -> "1") ++ ldLibPath
    }
  )

