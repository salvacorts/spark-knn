import Common._

lazy val root = Project("spark-knn", file(".")).
  settings(commonSettings).
  settings(Dependencies.Versions).
  aggregate(core, examples)

lazy val core = knnProject("spark-knn-core").
  settings(
    name := "spark-knn",
    spName := "salvacorts/spark-knn",
    credentials += Credentials(Path.userHome / ".ivy2" / ".sbtcredentials"),
    licenses += "Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0")
  ).
  settings(Dependencies.core).
  settings(
    scalafixDependencies in ThisBuild += "org.scalatest" %% "autofix" % "3.1.0.0",
    addCompilerPlugin(scalafixSemanticdb) // enable SemanticDB
  )

lazy val examples = knnProject("spark-knn-examples").
  dependsOn(core).
  settings(fork in run := true, coverageExcludedPackages := ".*examples.*").
  settings(Dependencies.examples).
  settings(SparkSubmit.settings: _*)