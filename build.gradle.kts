import org.cthing.gradle.plugins.buildconstants.SourceAccess

plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.0"
    id("org.cthing.build-constants") version "2.1.0"
}

group = "online.bteisrael"
version = "Alef-2"

var minestomVersion = "2025.10.31-1.21.10"
var terraMinusMinusVersion = "2.0.0-1.21.4"

repositories {
    mavenCentral()
    maven("https://maven.smyler.net/releases/")
    maven("https://maven.daporkchop.net/")
}

dependencies {
    // Minestom
    implementation("net.minestom:minestom:${minestomVersion}")

    // "Real World" Generator
    implementation("net.buildtheearth.terraminusminus:terraminusminus-core:${terraMinusMinusVersion}")

    // Configuration
    implementation("org.spongepowered:configurate-hocon:4.2.0")

    // Logger
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
    generateBuildConstants {
        classname = "online.bteisrael.satellite.SatelliteConstants"
        sourceAccess = SourceAccess.PUBLIC
        additionalConstants.put("SATELLITE_VERSION", version)
        additionalConstants.put("MINESTOM_VERSION", minestomVersion)
        additionalConstants.put("TERRA_MINUS_MINUS_VERSION", terraMinusMinusVersion)
    }
}