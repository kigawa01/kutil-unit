/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
  // Support convention plugins written in Kotlin. Convention plugins are build scripts in 'src/main' that automatically become available as plugins in the main build.
  `kotlin-dsl`
  id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
}
dependencies{
  implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.23")
}

repositories {
  // Use the plugin portal to apply community plugins in convention plugins.
  gradlePluginPortal()
}