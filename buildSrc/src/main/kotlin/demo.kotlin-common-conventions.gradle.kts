
plugins {
    id("org.jetbrains.kotlin.jvm") // <1>
}

repositories {
    mavenCentral() // <2>
}

dependencies {
    constraints {
        implementation("org.apache.commons:commons-text:1.9") // <3>
    }

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1") // <4>
}

tasks.named<Test>("test") {
    useJUnitPlatform() // <5>
}
