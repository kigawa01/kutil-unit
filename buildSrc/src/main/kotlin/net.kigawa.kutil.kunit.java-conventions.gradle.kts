/*
 * This file was generated by the Gradle 'init' task.
 */
import dependencies.ProjectConfig
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

plugins {
  kotlin("multiplatform")
}

repositories {
  mavenLocal()
  mavenCentral()

  maven {
    url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
  }
}

dependencies {
  commonMainImplementation("org.jetbrains.kotlin:kotlin-stdlib")
  commonTestImplementation(kotlin("test-common"))
  commonMainImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1-Beta")
  // https://mvnrepository.com/artifact/net.kigawa.kutil/kutil
  commonMainImplementation("net.kigawa.kutil:kutil:4.0.2")
  // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-reflect
  commonMainImplementation("org.jetbrains.kotlin:kotlin-reflect:1.9.23")
}

version = ProjectConfig.VERSION
group = ProjectConfig.GROUP


kotlin {
  jvm("jvm") {
  }
  js("js") {
    browser {}
    nodejs { }
  }

  sourceSets {
    val jvmMain by getting {
      dependencies {
//        implementation("net.kigawa.kutil:kutil:2.2.2")
      }
    }
    val jvmTest by getting {
      dependencies {
        implementation(kotlin("test-junit5"))
      }
    }
    val commonMain by getting
    val commonTest by getting
  }
}



tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = "${JavaVersion.VERSION_17}"
    }
  }
  withType<KotlinCompile<*>> {
    kotlinOptions {
      freeCompilerArgs += "-Xexpect-actual-classes"
    }
  }
  withType<Test> {
    useJUnitPlatform()
  }
}