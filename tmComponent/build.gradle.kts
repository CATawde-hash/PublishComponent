import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    id("org.jetbrains.compose")
    id("kotlin-parcelize")
    id("org.jetbrains.dokka") version "1.9.10"
    id("maven-publish")
    id("signing")
}



group = "com.example.publishcomponent"
version = "1.0.0"

/*
val artifact = "publishComponent"
val pkgUrl = "https://github.com/CATawde-hash/PublishComponent"
val gitUrl = "github.com:CATawde-hash/PublishComponent.git"
val ktorVersion = "1.0.0"
*/


kotlin {
    androidTarget {
        publishLibraryVariants("release", "debug")

        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "tmComponent"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.components.resources)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}



android {
    namespace = "com.example.publishcomponent"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.ui.graphics.android)
}



/*
publishing {
    publications {
        create<MavenPublication>("mavenLibrary") {
            groupId = "io.github.catawde-hash"
            artifactId = "publishComponent"
            version = "1.0.0"

            pom {
                name.set("PublishComponent")
                description.set("Publish Component Library")
                url.set("https://github.com/CATawde-hash/PublishComponent")

                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("CATawde-hash")
                        name.set("chandana")
                        email.set("chandana.tawde@truemeds.in")
                    }
                }
                scm {
                    url.set("https://github.com/CATawde-hash/PublishComponent")
                }

            }
        }
    }
    repositories {
        maven {
            name = "MyMavenRepo"
            url = uri("file:///${System.getProperty("user.home")}/.m2/repository") // Example for local repo
            // Or use credentials for remote repos
            // credentials {
            //     username = "your-username"
            //     password = "your-password"// }
        }
    }
    // Configure publishing to Maven Central
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    // Enable GPG signing for all publications
    signAllPublications()
}

*/
/*

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "io.github.catawde-hash"
            artifactId = "publishComponent"
            version = "1.0.0"

            from(components["release"]) // Typically your release component
        }
    }
    repositories {
        maven {
            name = "MavenCentral"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("MDv8WbOJ")
                password = System.getenv("0YGwJPsf9wyUxNKmhsbLTrrC2oT+jP1RaxZwQB800NbH")
            }
        }
    }
}
*//*


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "io.github.catawde-hash"
            artifactId = "publishComponent"
            version = "1.0.0"

            from(components["release"]) // or "debug"
        }
    }
    repositories {
        maven {
            name = "MavenCentral"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("MDv8WbOJ")
                password = System.getenv("0YGwJPsf9wyUxNKmhsbLTrrC2oT+jP1RaxZwQB800NbH")
            }
        }
    }
}
*/
/*
mavenPublishing {
    // Define coordinates for the published artifact
    coordinates(
        groupId = "<YOUR_SONATYPE_CENTRAL_NAMESPACE>",
        artifactId = "math-lib",
        version = "1.0.0"
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set("Math KMP Library")
        description.set("Sample Kotlin MultiPlatform Library Test")
        inceptionYear.set("2024")
        url.set("https://github.com/<GITHUB_USER_NAME>/MathLibGuide")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        // Specify developers information
        developers {
            developer {
                id.set("<GITHUB_USER_NAME>")
                name.set("<GITHUB_ACTUAL_NAME>")
                email.set("<GITHUB_EMAIL_ADDRESS>")
            }
        }

        // Specify SCM information
        scm {
            url.set("https://github.com/<GITHUB_USER_NAME>/MathLibGuide")
        }
    }

    // Configure publishing to Maven Central
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    // Enable GPG signing for all publications
    signAllPublications()
}*/

val dokkaOutputDir = buildDir.resolve("dokka")
tasks.dokkaHtml { outputDirectory.set(file(dokkaOutputDir)) }
val deleteDokkaOutputDir by tasks.register<Delete>("deleteDokkaOutputDirectory") { delete(dokkaOutputDir) }
val javadocJar = tasks.create<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(deleteDokkaOutputDir, tasks.dokkaHtml)
    from(dokkaOutputDir)
}


publishing {
    publications {
        publications.withType<MavenPublication> {
            artifact(javadocJar)

            pom {
                name.set("PublishComponent") // Change here
                description.set("Publish Component") // Change here
                url.set("https://github.com/CATawde-hash/PublishComponent") // Change here

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0") // Change here, if needed
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt") // Change here, if needed
                    }
                }

                issueManagement {
                    system.set("GitHub Issues")
                    url.set("https://github.com/CATawde-hash/PublishComponent/issues") // Change here
                }

                developers {
                    developer {
                        id.set("CATawde-hash") // Change here
                        name.set("Chandana") // Change here
                        email.set("chandana.tawde@truemeds.in") // Change here
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com:CATawde-hash/PublishComponent.git") // Change here
                    developerConnection.set("scm:git:ssh://github.com:CATawde-hash/PublishComponent.git")// Change here
                    url.set("https://github.com/CATawde-hash/PublishComponent") // Change here
                }
            }
        }
    }
}

signing {
    if (project.hasProperty("signing.gnupg.keyName")) {
        println("Signing lib...")
        useGpgCmd()
        sign(publishing.publications)
    }
}
