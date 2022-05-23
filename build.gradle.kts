plugins {
    kotlin("js") version "1.6.20"
}

group = "me.cjgj"
version = "1.0.0"

repositories {
    mavenCentral()
}

val kotlinReactVersion: String by project
dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$kotlinReactVersion")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$kotlinReactVersion")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-css:$kotlinReactVersion")
}

kotlin {
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}