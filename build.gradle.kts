plugins {
    `java-library`
    `maven-publish`
    id("org.jetbrains.kotlin.jvm") version "1.5.21"
}


group = "com.kmccarp.recipe.example"
version = "0.1.0-SNAPSHOT"
description = "Example Rewrite recipes."

repositories {
    mavenLocal()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }
    mavenCentral()
}

configurations.all {
    resolutionStrategy {
        cacheChangingModulesFor(0, TimeUnit.SECONDS)
        cacheDynamicVersionsFor(0, TimeUnit.SECONDS)
    }
}

val rewriteVersion = "latest.release"

dependencies {
    compileOnly("org.projectlombok:lombok:latest.release")
    annotationProcessor("org.projectlombok:lombok:latest.release")

    implementation("org.openrewrite:rewrite-java:${rewriteVersion}")
    runtimeOnly("org.openrewrite:rewrite-java-11:${rewriteVersion}")

    testImplementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.junit.jupiter:junit-jupiter-api:latest.release")
    testImplementation("org.junit.jupiter:junit-jupiter-params:latest.release")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:latest.release")

    testImplementation("org.openrewrite:rewrite-test:${rewriteVersion}")
    testImplementation("org.assertj:assertj-core:latest.release")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    jvmArgs = listOf("-XX:+UnlockDiagnosticVMOptions", "-XX:+ShowHiddenFrames")
}
