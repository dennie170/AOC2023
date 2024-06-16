plugins {
    kotlin("jvm") version "2.0.0"
    application
}

group = "org.dennie170"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(22)
}

application {
    mainClass.set("com.dennie170.Runner")
}

tasks.jar {
    manifest.attributes["Main-Class"] = "com.dennie170.Runner"
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree) // OR .map { zipTree(it) }
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
