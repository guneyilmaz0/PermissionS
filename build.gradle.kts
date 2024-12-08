plugins {
    kotlin("jvm") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "net.guneyilmaz0.permissions"
version = "1.1"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.opencollab.dev/maven-releases")
    }
    maven {
        url = uri("https://repo.opencollab.dev/maven-snapshots")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.0.0")
    implementation("com.google.code.gson:gson:2.10.1")
    testImplementation(kotlin("test"))
    compileOnly(files("libs/powernukkitx-2.0.0-SNAPSHOT-all.jar"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    mergeServiceFiles()
}

kotlin {
    jvmToolchain(21)
}