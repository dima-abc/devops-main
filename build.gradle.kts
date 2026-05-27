plugins {
    checkstyle
    java
    jacoco
    alias(libs.plugins.springBoot)
    alias(libs.plugins.springDependencyManagement)
    alias(libs.plugins.spotBugs)
}

group = "ru.job4j.devops"
version = "1.0.0"

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.8".toBigDecimal()
            }
        }

        rule {
            isEnabled = false
            element = "CLASS"
            includes = listOf("org.gradle.*")

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "0.3".toBigDecimal()
            }
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.springBootStarterWeb)
    implementation(libs.springBootStartrDataJpa)
    implementation(libs.liquiabase)
    implementation(libs.postgresql)
    testImplementation(libs.springBootStarterTest)
    testRuntimeOnly(libs.junitPlatformLauncher)
    testImplementation(libs.junitJupiter)
    testImplementation(libs.assertj)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register<Zip>("zipJavaDoc") {
    group = "documentation" // Группа, в которой будет отображаться задача
    description = "Packs the generated Javadoc into a zip archive"

    dependsOn("javadoc") // Указываем, что задача зависит от выполнения javadoc

    from("build/docs/javadoc") // Исходная папка для упаковки
    archiveFileName.set("javadoc.zip") // Имя создаваемого архива
    destinationDirectory.set(layout.buildDirectory.dir("archives")) // Директория, куда будет сохранен архив
}

tasks.spotbugsMain {
    reports.create("html") {
        required = true
        outputLocation.set(layout.buildDirectory.file("reports/spotbugs/spotbugs.html"))
    }
}

tasks.register("hello") {
    group = "Custom Tasks" // Группа для удобства в './gradlew tasks'
    description = "Prints hello message"
    doLast {
        println("Hello world from Gradle!")
    }
}

tasks.register("cleanReports") {
    group = "Cleanup"
    description = "Deletes all report files"
    doLast {
        file("build/reports").deleteRecursively()
        println("Reports deleted")
    }
}

val isDev = project.hasProperty("dev")
if (isDev) {
    println("Development environment: detected")
}

tasks.withType<JavaExec> {
    jvmArgs("-Xms512m", "-Xmx1024m")
}

tasks.register("checkJarSize") {
    group = "verification"
    description = "Checks the size of the generated JAR file."
    dependsOn("jar")
    doLast {
        val jarFile = file("build/libs/${project.name}-${project.version}.jar")
        if(jarFile.exists()) {
            val sizeInMb = jarFile.length() / (1024.0 * 1024.0)
            if(sizeInMb > 5) {
                println("WARNING: JAR file exceeds the size limit of 5 MB. Current size: $sizeInMb MB")
            } else {
                println("JAR file is within the acceptable size limit. Current size: $sizeInMb MB")
            }
        } else {
            println("JAR file not not found. Please make sure the build process completed successfully.")
        }
    }
}

tasks.jar {
    finalizedBy("checkJarSize")
}