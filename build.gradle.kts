import nl.littlerobots.vcu.plugin.resolver.VersionSelectors

plugins {
    java
    application
    alias(libs.plugins.shadow)
    alias(libs.plugins.spotless)
    alias(libs.plugins.version.catalog.update)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.picocli)
    annotationProcessor(libs.picocli.codegen)

    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

testing {
    suites {
        val test =
            named<JvmTestSuite>("test") {
                useJUnitJupiter("6.0.1")
            }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "ro.msg4banking.App"
}

tasks.shadowJar {
    archiveBaseName = "alfred"
    archiveClassifier = ""
    mergeServiceFiles()
    manifest {
        attributes(
            "Implementation-Title" to "alfred",
            "Implementation-Version" to project.version,
        )
    }
}

tasks.jar {
    archiveClassifier = "thin"
}

spotless {
    java {
        target("src/**/*.java")
        googleJavaFormat()
        removeUnusedImports()
        formatAnnotations()
        trimTrailingWhitespace()
        endWithNewline()
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}

versionCatalogUpdate {
    sortByKey.set(true)
    versionSelector(VersionSelectors.STABLE)
    keep {
        keepUnusedVersions.set(true)
    }
}
