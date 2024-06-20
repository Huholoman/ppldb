plugins {
    id("java")
}

group = "org.epptec.ppldb"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Testovací závislosti
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
