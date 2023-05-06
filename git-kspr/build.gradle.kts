import com.expediagroup.graphql.plugin.gradle.config.GraphQLParserOptions
import com.expediagroup.graphql.plugin.gradle.config.GraphQLSerializer
import com.expediagroup.graphql.plugin.gradle.tasks.GraphQLDownloadSDLTask
import com.expediagroup.graphql.plugin.gradle.tasks.GraphQLGenerateClientTask

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"
    application
    id("com.expediagroup.graphql") version "6.4.1"
}

repositories {
    mavenCentral()
}

//val graphqlDownloadSDL by tasks.getting(GraphQLDownloadSDLTask::class) {
//    endpoint.set("https://docs.github.com/public/schema.docs.graphql")
//}

graphql {
    client {
        sdlEndpoint = "https://docs.github.com/public/schema.docs.graphql"
        queryFileDirectory = "src/graphql"
        packageName = "sims.michael.gitkspr.generated"
        serializer = GraphQLSerializer.KOTLINX
    }
}

//val graphqlGenerateClient by tasks.getting(GraphQLGenerateClientTask::class) {
//    // we need to overwrite default behavior of using Jackson data model
//    serializer.set(GraphQLSerializer.KOTLINX)
//    packageName.set("com.expediagroup.graphql.generated")
////    parserOptions.set(GraphQLParserOptions(maxTokens = Int.MAX_VALUE))
//}

dependencies {
    implementation("com.github.ajalt.clikt:clikt:3.5.2")
    implementation("com.expediagroup:graphql-kotlin-ktor-client:6.4.1")
    implementation("io.ktor:ktor-client-auth:2.3.0")
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("ch.qos.logback:logback-classic:1.4.6")


    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.1")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("sims.michael.gitkspr.MainKt")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
