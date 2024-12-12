plugins {
    kotlin("plugin.spring") version "2.0.0"
    id("io.spring.dependency-management") version "1.1.6"
    id("java-library")
}

group = "my.test.common"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("org.springframework:spring-context")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-autoconfigure")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.1")
    api("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.1")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.4.0")
    }
}