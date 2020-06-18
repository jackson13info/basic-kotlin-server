package basicserver

import basicserver.HealthChecks.BasicHealthCheck
import basicserver.routes.UniversalLinks
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.dropwizard.Application
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.jersey.setup.JerseyEnvironment
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import org.glassfish.jersey.media.multipart.MultiPartFeature
import org.skife.jdbi.v2.DBI


fun main(args: Array<String>) {
    ServerApp().run(*args)
}

class ServerApp : Application<ServerConfig>() {

    override fun initialize(bootstrap: Bootstrap<ServerConfig>) {
        bootstrap.objectMapper.registerModule(KotlinModule())
    }

    override fun run(configuration: ServerConfig, environment: Environment) {
        println("Running ${configuration.name}!")

        // Register Database
        val jdbi = registerDatabase(configuration, environment)
        val database = BasicDatabase(jdbi)

        // Setup Parsing
        registerParsers(environment.jersey())

        // Setup Endpoints
        registerRoutes(configuration, environment)

        // Register Health Checks
        registerHealthChecks(environment)
    }

    private fun registerParsers(jerseyEnvironment: JerseyEnvironment) {
        jerseyEnvironment.register(MultiPartFeature::class.java)
    }

    private fun registerHealthChecks(environment: Environment) {
        environment.healthChecks().register("BasicHealthCheck", BasicHealthCheck())
    }

    private fun registerDatabase(configuration: ServerConfig, environment: Environment): DBI {
        val factory = DBIFactory()
        return factory.build(environment, configuration.database, "")
    }

    private fun registerRoutes(
        configuration: ServerConfig,
        environment: Environment
    ) {
        environment.run {
            jersey().register(UniversalLinks())
        }
    }
}