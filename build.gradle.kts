buildscript {
    repositories {
        maven("https://maven.aliyun.com/repository/central")
    }
}

plugins {
    `java-library`
    `maven-publish`
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral()
    maven("https://repo.tabooproject.org/repository/releases")
}

val taboolibVersion: String by project
dependencies {
    compileOnly("ink.ptms:nms-all:1.0.0")
    compileOnly("ink.ptms.core:v11902:11902-minimize:mapped")
    compileOnly("ink.ptms.core:v11902:11902-minimize:universal")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))

    compileOnly("org.tabooproject.taboolib:module-parrotx:1.5.4")

    compileOnly("io.izzel.taboolib:common:${taboolibVersion}")
    compileOnly("io.izzel.taboolib:common-legacy-api:${taboolibVersion}")
    compileOnly("io.izzel.taboolib:common-env:${taboolibVersion}")
    compileOnly("io.izzel.taboolib:common-platform-api:${taboolibVersion}")
    compileOnly("io.izzel.taboolib:common-reflex:${taboolibVersion}")
    compileOnly("io.izzel.taboolib:common-util:${taboolibVersion}")
    compileOnly("io.izzel.taboolib:module-bukkit-util:${taboolibVersion}")
    compileOnly("io.izzel.taboolib:module-configuration:${taboolibVersion}")
    compileOnly("io.izzel.taboolib:module-chat:${taboolibVersion}")
    compileOnly("io.izzel.taboolib:module-kether:${taboolibVersion}")
    compileOnly("io.izzel.taboolib:module-metrics:${taboolibVersion}")
    compileOnly("io.izzel.taboolib:module-nms:${taboolibVersion}")
    compileOnly("io.izzel.taboolib:module-ui:${taboolibVersion}")
    compileOnly("io.izzel.taboolib:platform-bukkit:${taboolibVersion}")
}

java {
    withSourcesJar()
}

tasks {
    build {
        dependsOn("shadowJar")
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjvm-default=all")
        }
    }

    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveClassifier.set("")
        relocate("taboolib.", "com.mcstarrysky.taboolib.")
    }
}

publishing {
    repositories {
        maven {
            url = uri("https://repo.tabooproject.org/repository/releases")
            credentials {
                username = project.findProperty("taboolibUsername").toString()
                password = project.findProperty("taboolibPassword").toString()
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            version = (if (project.hasProperty("build")) {
                var build = project.findProperty("build").toString()
                if (build.startsWith("task ")) {
                    build = "local"
                }
                "${project.version}-$build"
            } else {
                "${project.version}"
            })

            artifact(tasks["kotlinSourcesJar"])
            artifact(tasks["shadowJar"])
        }
    }
}