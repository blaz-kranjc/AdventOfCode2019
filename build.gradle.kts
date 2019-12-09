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

task("day3", JavaExec::class) {
    group = "aoc"
    main = "day3.Day3Kt"
    classpath = sourceSets["main"].runtimeClasspath
}

task("day4", JavaExec::class) {
    group = "aoc"
    main = "day4.Day4Kt"
    classpath = sourceSets["main"].runtimeClasspath
}

task("day5", JavaExec::class) {
    group = "aoc"
    main = "day5.Day5Kt"
    classpath = sourceSets["main"].runtimeClasspath
}

task("day6", JavaExec::class) {
    group = "aoc"
    main = "day6.Day6Kt"
    classpath = sourceSets["main"].runtimeClasspath
}

task("day7", JavaExec::class) {
    group = "aoc"
    main = "day7.Day7Kt"
    classpath = sourceSets["main"].runtimeClasspath
}

task("day8", JavaExec::class) {
    group = "aoc"
    main = "day8.Day8Kt"
    classpath = sourceSets["main"].runtimeClasspath
}

task("day9", JavaExec::class) {
    group = "aoc"
    main = "day9.Day9Kt"
    classpath = sourceSets["main"].runtimeClasspath
}
