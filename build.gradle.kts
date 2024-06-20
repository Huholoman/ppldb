plugins {
    id("java")
}

group = "org.epptec.ppldb"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-context:6.1.10")

    implementation("org.springframework.boot:spring-boot-starter-web:3.3.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

java {
    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
    }
}
