plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation("org.springframework.security:spring-security-test")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation ("org.jsoup:jsoup:1.14.3")


    implementation("org.hibernate.validator:hibernate-validator:7.0.5.Final")
    implementation("org.glassfish:jakarta.el:4.0.1")
    testImplementation("junit:junit:4.13.1")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.0")
    implementation ("com.rabbitmq:amqp-client:5.13.1")

    implementation ("javax.servlet:javax.servlet-api:4.0.1")
    testImplementation("com.h2database:h2")
}

val integrationTestTask = tasks.register<Test>("integrationTest") {
    group = "verification"
    filter {
        includeTestsMatching("*Integration")
    }
}

tasks.check {
    dependsOn(integrationTestTask)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
