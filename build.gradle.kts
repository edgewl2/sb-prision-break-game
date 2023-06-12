plugins {
    java
    jacoco
    groovy
    id("org.springframework.boot") version "3.1.1-SNAPSHOT"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "ni.dev.edgeahz.game"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.1.0")
    implementation("org.flywaydb:flyway-core:9.19.4")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.postgresql:r2dbc-postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.spockframework:spock-core:2.4-M1-groovy-3.0")
    testImplementation("org.spockframework:spock-spring:2.4-M1-groovy-3.0")
}

tasks.withType<Test> {
    finalizedBy(tasks.getByName<JacocoReport>("jacocoTestReport"))
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    sourceSets(sourceSets.main.get())
    executionData(fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec"))

    dependsOn(tasks.test)
    reports {
        xml.required.set(true);
        html.required.set(true);
    }

    ignorePackagesInJacocoReport(classDirectories)
}

fun ignorePackagesInJacocoReport(classDirectories: ConfigurableFileCollection) {
    classDirectories.setFrom(
            files(classDirectories.files.map {
                fileTree(it).apply {
                    exclude(
                            "**/edgeahz/**/config/**",
                            "**/edgeahz/**/domain/**",
                            "**/edgeahz/**/repository/**",
                            "**/edgeahz/**/PrisonGameApplication.class"
                    )
                }
            })
    )
}

