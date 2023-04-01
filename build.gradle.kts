val projectVersion: String by project
val springBootVersion: String by project
val logbackClassicVersion: String by project
val javafxSwingVersion: String by project

plugins {
	java
	application
	id("org.springframework.boot") version "2.4.5"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "com.school21"
version = projectVersion
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
	maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
	implementation("org.openjfx:javafx-swing:$javafxSwingVersion")
	implementation("ch.qos.logback:logback-classic:$logbackClassicVersion")
	implementation("org.projectlombok:lombok:1.18.26")
	implementation("com.fifesoft:rsyntaxtextarea:3.3.3")
	implementation("com.fifesoft:autocomplete:3.3.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

}

application {
	mainClass.set("com.school21.simplewebstudio.SimpleWebStudioApplication")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
