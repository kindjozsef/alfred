# new-cli-app

A starter template for building command-line applications in Java with Gradle.


# Getting started

Clone the template, then rename the bits that identify the project:
- rootProject.name in settings.gradle.kts
- the ro.kindjozsef package under src/main/java and src/test/java
- mainClass and archiveBaseName in build.gradle.kts
- @Command(name = ...) in App.java

# The version catalog
All dependency and plugin versions live in gradle/libs.versions.toml. The build file then refers to them symbolically:

```shell
dependencies {
    implementation(libs.picocli)
}
```
## Keeping versions up to date
The version-catalog-update plugin resolves the latest available versions and rewrites the TOML file:

```shell
# update every entry, then reformat and sort the catalog
./gradlew versionCatalogUpdate
```

# Building a fat JAR
The Shadow plugin packages the application together with all of its runtime dependencies into one self-contained JAR, so it can be run anywhere a JVM is available without a classpath to assemble.

```shell
./gradlew shadowJar
```

The output lands in `build/libs/new-cli-app.jar`:
```shell
java -jar build/libs/new-cli-app.jar --help
```

# Running this application

## Development
```shell
./gradlew run --args="input.txt -n 3 --verbose"
```

## From the packaged JAR
```shell
./gradlew shadowJar
java -jar build/libs/new-cli-app.jar input.txt -n 3 --verbose
```
