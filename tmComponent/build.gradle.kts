plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    id("maven-publish")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")

}

group = "com.example.publishcomponent"
version = "0.1.0"

kotlin {
    androidTarget {
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
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
            groupId = "com.example.publishcomponent"
            artifactId = "PublishComponent"
            version = "0.1.0"
            // Add pom configuration if needed
            //pom {
              //  name.set("PublishComponent")
                //description.set("A Kotlin Multiplatform Mobile Library")
                //url.set("https://gitlab.com/ChandanaTawde/publishcomponent.git")
            //}

        }
    }
/*
    repositories {
        maven {
            url = uri("https://gitlab.com/api/v4/projects/61331517/packages/maven")
            credentials(HttpHeaderCredentials::class) {
                name = "Private-Token"
                value = System.getenv("glpat-QfFj6Pd8zyYe9pwPiy8K")
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
    }
*/
}
