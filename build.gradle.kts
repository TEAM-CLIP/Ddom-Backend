import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin(Plugins.JVM.module) version Plugins.JVM.version

    id(Plugins.SPRING_BOOT.module) version Plugins.SPRING_BOOT.version
    id(Plugins.SPRING_DEPENDENCY_MANAGEMENT.module) version Plugins.SPRING_DEPENDENCY_MANAGEMENT.version

    kotlin(Plugins.KOTLIN_SPRING.module) version Plugins.KOTLIN_SPRING.version
    kotlin(Plugins.KOTLIN_JPA.module) version Plugins.KOTLIN_JPA.version

    // test fixtures
    `java-test-fixtures`
}


allprojects {
    group = "com.ddom"
    version = ""

    tasks.withType<JavaCompile> {
        sourceCompatibility = Versions.JAVA_VERSION
        targetCompatibility = Versions.JAVA_VERSION
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = Versions.JAVA_VERSION
        }
    }

    tasks.withType<BootJar> {
        enabled = Modules.BOOTSTRAP_MODULES.contains("${Modules.BOOTSTRAP_BASE_MODULE}:${project.name}")
    }


    repositories {
        mavenCentral()
    }

    apply {
        plugin(Plugins.JVM.id)
        plugin(Plugins.KOTLIN_SPRING.id)
        plugin(Plugins.SPRING_BOOT.id)
        plugin(Plugins.SPRING_DEPENDENCY_MANAGEMENT.id)
        plugin(Plugins.TEST_FIXTURES.id)
    }

    dependencies {
        implementation(Dependencies.Spring.BOOT)
        implementation(Dependencies.Kotlin.KOTLIN_REFLECT)
        testImplementation(Dependencies.Spring.TEST)
        testFixturesImplementation(Dependencies.Spring.TEST)
        testRuntimeOnly(Dependencies.Junit.JUNIT_LAUNCHER)
        testImplementation(Dependencies.Kotlin.KOTLIN_TEST_JUNIT5)
        testFixturesImplementation(Dependencies.Kotlin.KOTLIN_TEST_JUNIT5)
        testImplementation(Dependencies.Kotest.KOTEST_RUNNER)
        testImplementation(Dependencies.Kotest.KOTEST_ASSERTIONS_CORE)
        implementation(Dependencies.Jackson.KOTLIN)
        implementation(Dependencies.Jackson.JAVA_TIME)
        testImplementation(Dependencies.Kotest.KOTEST_PROPERTY)
        testImplementation(Dependencies.Mockk.MOCKK)
        implementation(Dependencies.Logger.KOTLIN_OSHAI)
    }

    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

}



