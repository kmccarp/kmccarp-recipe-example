plugins {
    `java-library`
    java
    `maven-publish`
    id("org.jetbrains.kotlin.jvm") version "1.5.21"
    id("io.moderne.rewrite") version "0.29.0"
}


group = "com.kmccarp.recipe.example"
version = "0.2.0-SNAPSHOT"
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

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

val rewriteVersion = "7.29.0"

dependencies {
    compileOnly("org.projectlombok:lombok:latest.release")
    annotationProcessor("org.projectlombok:lombok:latest.release")

    implementation("org.openrewrite:rewrite-java:${rewriteVersion}")
    implementation("org.openrewrite:rewrite-xml:${rewriteVersion}")
    implementation("org.openrewrite:rewrite-maven:${rewriteVersion}")
    runtimeOnly("org.openrewrite:rewrite-java-11:${rewriteVersion}")

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

publishing {
    repositories {
        maven {
            url = uri("https://artifactory.moderne.ninja/artifactory/kevin-local/")
            credentials {
                username = property("artifactory.username").toString()
                password = property("artifactory.password").toString()
            }
        }
    }
    publications {
        create<MavenPublication>("library") {
            from(components["java"])
        }
    }
}
