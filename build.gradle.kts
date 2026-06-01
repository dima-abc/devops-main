repositories {
    mavenCentral()
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(libs.liquiabase)
    }
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.springBootStarterWeb)
    implementation(libs.springBootStartrDataJpa)
    implementation(libs.liquiabase)
    implementation(libs.postgresql)
    implementation(libs.sprringKafka)
    testImplementation(libs.springBootStarterTest)
    testRuntimeOnly(libs.junitPlatformLauncher)
    testImplementation(libs.junitJupiter)
    testImplementation(libs.assertj)
    testImplementation(libs.testcontainersPostgresql)
    testImplementation(libs.testcontainersKafka)
    testImplementation(libs.awaitility)
    liquibaseRuntime(libs.liquiabase)
    liquibaseRuntime(libs.postgresql)
    liquibaseRuntime(libs.jaxbiApi)
    liquibaseRuntime(libs.logbackCore)
    liquibaseRuntime(libs.logbackClassic)
    liquibaseRuntime(libs.picocli)
}

plugins {
    checkstyle
    java
    jacoco
    alias(libs.plugins.springBoot)
    alias(libs.plugins.springDependencyManagement)
    alias(libs.plugins.spotBugs)
    alias(libs.plugins.liquibaseGradle)
    alias(libs.plugins.uzzuDotenv)
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

tasks.register("cleanReports") {
    group = "Cleanup"
    description = "Deletes all report files"
    doLast {
        file("build/reports").deleteRecursively()
        println("Reports deleted")
    }
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
        if (jarFile.exists()) {
            val sizeInMb = jarFile.length() / (1024.0 * 1024.0)
            if (sizeInMb > 5) {
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

liquibase {
    activities.register("main") {
        this.arguments = mapOf(
            "logLevel" to env.LOG_LEVEL.value,
            "url" to env.DB_URL.value,
            "username" to env.DB_USERNAME.value,
            "password" to env.DB_PASSWORD.value,
            "defaultSchemaName" to "devops",
            "classpath" to "src/main/resources",
            "changelogFile" to "db/changelog/changelog-master.xml"
        )
    }
    runList = "main"
}

tasks.register("profile") {
    doFirst {
        println(env.DB_URL.value)
    }
}

tasks.named<Test>("test") {
    systemProperty("spring.datasource.url", env.DB_URL.value)
    systemProperty("spring.datasource.username", env.DB_USERNAME.value)
    systemProperty("spring.datasource.password", env.DB_PASSWORD.value)
}

// Зависимости для интеграционных тестов
sourceSets {
    val integrationTest by sourceSets.creating {
        java.srcDir("src/integrationTest/java")
        resources.srcDir("src/integrationTest/resources")
        DuplicatesStrategy.INCLUDE
        compileClasspath += sourceSets["main"].output + sourceSets["test"].output
        runtimeClasspath += sourceSets["main"].output + sourceSets["test"].output
    }
}
val integrationTest = sourceSets["integrationTest"]
configurations {
    val integrationTestImplementation by configurations.getting {
        extendsFrom(configurations["testImplementation"])
    }
    val integrationTestRuntimeOnly by configurations.getting {
        extendsFrom(configurations["testRuntimeOnly"])
    }
}

tasks.register<Test>("integrationTest") {
    description = "Runs the integration tests."
    group = "verification"

    testClassesDirs = integrationTest.output.classesDirs
    classpath = integrationTest.runtimeClasspath
    shouldRunAfter(tasks.test)
}

tasks.check {
    dependsOn("integrationTest")
}

tasks.withType<Copy>().configureEach {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}