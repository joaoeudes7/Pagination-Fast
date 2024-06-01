plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
    `ivy-publish`
}

afterEvaluate {
    publishing {
        publications {
            val organizationGroup = "com.github.joaoeudes7"
            val artifactIdPkg = "pagination-fast"
            val versionPkg = "1.0.3"

            create<MavenPublication>("maven") {
                groupId = organizationGroup
                artifactId = artifactIdPkg
                version = versionPkg

                from(components.findByName("release"))
            }

            create<IvyPublication>("ivy") {
                organisation = organizationGroup
                module = artifactIdPkg
                revision = versionPkg

                from(components.findByName("release"))
            }
        }

        repositories {
            mavenLocal()
        }
    }
}

android {
    namespace = "com.jedev.paginationfast"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "consumer-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")

    // Pagination
    implementation("androidx.paging:paging-runtime-ktx:3.3.0")
    implementation("androidx.paging:paging-compose:3.3.0")

}