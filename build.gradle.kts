import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.epptec.ppldb"
version = "1.0"

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

val shadowJarTaskProvider = tasks.named<ShadowJar>("shadowJar")

val buildCli by tasks.creating(ShadowJar::class) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveBaseName.set("cli")
    archiveVersion.set(project.version.toString())

    manifest.attributes["Main-Class"] = "org.epptec.ppldb.CliMain"
    from(zipTree(shadowJarTaskProvider.get().archiveFile))
}


val buildHttp by tasks.creating(ShadowJar::class) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveBaseName.set("http")
    archiveVersion.set(project.version.toString())

    manifest.attributes["Main-Class"] = "org.epptec.ppldb.HttpMain"
    from(zipTree(shadowJarTaskProvider.get().archiveFile))
}

