plugins {
    kotlin("plugin.spring") version "2.0.0"
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "my.test.request.service"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-core")
    implementation("io.micrometer:micrometer-registry-prometheus")

    implementation("io.opentelemetry.instrumentation:opentelemetry-spring-boot-starter:2.10.0")
    implementation("io.opentelemetry.instrumentation:opentelemetry-kafka-clients-2.6:2.10.0-alpha")

    testImplementation("org.springframework.kafka:spring-kafka-test")
}
