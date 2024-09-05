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

val artifact = "publishComponent"
val pkgUrl = "https://github.com/CATawde-hash/PublishComponent"
val gitUrl = "github.com:CATawde-hash/PublishComponent.git"
val ktorVersion = "1.0.0"


val dokkaOutputDir = "$buildDir/dokka"

tasks.dokkaHtml {
    outputDirectory.set(file(dokkaOutputDir))
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
//    description = "Assembles Kotlin docs with Dokka"
    archiveClassifier.set("javadoc")
    from(tasks.dokkaHtml)
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")
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




publishing {
    repositories {
        maven {
            name = "Oss"
            setUrl {
                "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            }
            credentials {
                username = System.getenv("SONATYPE_USERNAME")
                password = System.getenv("SONATYPE_PASSWORD")
            }
        }
        maven {
            name = "Snapshot"
            setUrl { "https://s01.oss.sonatype.org/content/repositories/snapshots/" }
            credentials {
                username = System.getenv("SONATYPE_USERNAME")
                password = System.getenv("SONATYPE_PASSWORD")
            }
        }
    }

    publications {
        publications.configureEach {
            if (this is MavenPublication) {
                artifact(dokkaJar)
                pom {
                    name.set(artifact)
                    description.set("Publish Component Kotlin Multiplatform SDK")
                    url.set(pkgUrl)

                    licenses {
                        license {
                            name.set("MIT license")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }

                    issueManagement {
                        system.set("GitHub Issues")
                        url.set("$pkgUrl/issues")
                    }

                    developers {
                        developer {
                            id.set("CATawde-hash")
                            name.set("CATawde-hash")
                            email.set("chandana.tawde@truemeds.in")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://$gitUrl")
                        developerConnection.set("scm:git:ssh://$gitUrl")
                        url.set(pkgUrl)
                    }
                }
            }
        }
//        create<MavenPublication>("maven") {
//        withType<MavenPublication> {
//            groupId = "$group"
//            artifactId = artifact
//            version = version
//            artifact(dokkaJar)
//        }
    }
}

if (System.getenv("GPG_PRIVATE_KEY") != null && System.getenv("GPG_PRIVATE_PASSWORD") != null) {
    signing {
        useInMemoryPgpKeys(
            System.getenv("GPG_PRIVATE_KEY"),
            System.getenv("GPG_PRIVATE_PASSWORD")
        )
        sign(publishing.publications)
    }
}