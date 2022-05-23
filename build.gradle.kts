plugins {
    kotlin("js") version "1.6.21"
}

group = "me.cjgj"
version = "0.1.0"

repositories {
    mavenCentral()
}

val kotlinReactVersion: String by project
dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$kotlinReactVersion")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$kotlinReactVersion")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.9.0-pre.338")
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}