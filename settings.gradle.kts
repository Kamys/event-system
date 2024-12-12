plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "kafka-test"
include("common")
include("request-service")
include("parcel-service")
include("integration-tests")
include("proxy")
