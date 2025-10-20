plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.0"
}

group = "online.bteisrael"
version = "Alef-2"

var minestomVersion = "2025.10.18-1.21.10"

repositories {
    mavenCentral()
    maven("https://maven.smyler.net/releases/")
    maven("https://maven.daporkchop.net/")
}

dependencies {
    implementation("net.minestom:minestom:${minestomVersion}")

    implementation("net.buildtheearth.terraminusminus:terraminusminus-core:2.0.0-1.21.4")
    implementation("org.yaml:snakeyaml:2.5")

    implementation("org.tinylog:tinylog-api:2.8.0-M1")
    implementation("org.tinylog:tinylog-impl:2.8.0-M1")
    implementation("org.tinylog:slf4j-tinylog:2.8.0-M1")

    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
    implementation("com.google.guava:guava:33.5.0-jre")

    // Testing
    testImplementation("net.minestom:testing:${minestomVersion}")

    testCompileOnly("org.projectlombok:lombok:1.18.42")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.42")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "online.bteisrael.satellite.SatelliteServer"
        }
    }

    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("") // Prevent the -all suffix on the shadowjar file.
    }
}