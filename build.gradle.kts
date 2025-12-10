plugins {
    id("java")
    id("application")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("samplegame.SampleGame")
}

tasks.test {
    useJUnitPlatform()
}
