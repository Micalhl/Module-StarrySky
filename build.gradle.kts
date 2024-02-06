buildscript {
    repositories {
        maven("https://maven.aliyun.com/repository/central")
    }
}
plugins {
    `java-library`
    `maven-publish`
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
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

    implementation("io.izzel.taboolib:common:${taboolibVersion}")
    implementation("io.izzel.taboolib:common-legacy-api:${taboolibVersion}")
    implementation("io.izzel.taboolib:common-env:${taboolibVersion}")
    implementation("io.izzel.taboolib:common-platform-api:${taboolibVersion}")
    implementation("io.izzel.taboolib:common-reflex:${taboolibVersion}")
    implementation("io.izzel.taboolib:common-util:${taboolibVersion}")
    implementation("io.izzel.taboolib:module-bukkit-util:${taboolibVersion}")
    implementation("io.izzel.taboolib:module-configuration:${taboolibVersion}")
    implementation("io.izzel.taboolib:module-chat:${taboolibVersion}")
    implementation("io.izzel.taboolib:module-kether:${taboolibVersion}")
    implementation("io.izzel.taboolib:module-metrics:${taboolibVersion}")
    implementation("io.izzel.taboolib:module-nms:${taboolibVersion}")
    implementation("io.izzel.taboolib:module-ui:${taboolibVersion}")
    implementation("io.izzel.taboolib:platform-bukkit:${taboolibVersion}")
}

java {
    withSourcesJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjvm-default=all")
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
        create<MavenPublication>("library") {
            from(components["java"])
            version = (if (project.hasProperty("build")) {
                var build = project.findProperty("build").toString()
                if (build.startsWith("task ")) {
                    build = "local"
                }
                "${project.version}-$build"
            } else {
                "${project.version}"
            })
        }
    }
}