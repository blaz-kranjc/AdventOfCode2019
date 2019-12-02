import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.41"
    java
}

group = "bkranjc"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

task("day1", JavaExec::class) {
    group = "aoc"
    main = "day1.Day1Kt"
    classpath = sourceSets["main"].runtimeClasspath
}

task("day2", JavaExec::class) {
    group = "aoc"
    main = "day2.Day2Kt"
    classpath = sourceSets["main"].runtimeClasspath
}