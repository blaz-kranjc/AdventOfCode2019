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

    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

for (i in 1..13) {
    task("day${i}", JavaExec::class) {
        group = "aoc"
        main = "day${i}.Day${i}Kt"
        classpath = sourceSets["main"].runtimeClasspath
    }
}
