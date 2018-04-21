import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  var kotlin_version: String by extra
  kotlin_version = "1.2.40"

  repositories {
    mavenCentral()
  }
  dependencies {
    classpath(kotlin("gradle-plugin", kotlin_version))
  }
}

group = "com.github.cereda"
version = "0.1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

plugins {
  kotlin("jvm") version "1.2.31" // TODO: automate this variable
  application
}

val kotlin_version: String by extra

dependencies {
  implementation(kotlin("stdlib", kotlin_version))
  implementation(kotlin("stdlib-jdk8", kotlin_version))
  implementation(kotlin("runtime", kotlin_version))
  implementation(kotlin("reflect", kotlin_version))
}

application {
  // note: you must append Kt to the main class as this is the Kotlin compilation result
  mainClassName = "com.github.cereda.artexmis.AppMain"
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
  sourceSets {
    "main" {
      java.srcDirs(
          "src/main/kotlin"
      )
    }
    "resources" {
      java.srcDirs(
          "src/main/resources"
      )
    }
  }
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
  jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
  jvmTarget = "1.8"
}
