name := """login-micro-service"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  javaJdbc,
  guice,
  javaJpa,
  "org.hibernate" % "hibernate-core" % "5.2.17.Final",
  "mysql" % "mysql-connector-java" % "5.1.40",
  "org.jasypt" % "jasypt" % "1.9.2",
)
