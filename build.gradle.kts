import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    java
    kotlin("jvm") version "1.6.10"
}

repositories {
    mavenCentral() 
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.12.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
    this.testLogging {
        outputs.upToDateWhen {false}
        this.showStandardStreams = true
    }
}