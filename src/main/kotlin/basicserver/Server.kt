package basicserver

import basicserver.HealthChecks.BasicHealthCheck
import basicserver.routes.RootRoute
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.dropwizard.Application
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
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

        // Setup Endpoints
        registerRoutes(configuration, environment)

        // Register Health Checks
        registerHealthChecks(environment)
    }

    // Health checks are essentially just functions that run at the start of the server
    // to validate that everything is working. The basic health check that was created
    // here does nothing. Some examples of what you could do: connect to your database,
    // run some core functionality (unit tests), etc.
    private fun registerHealthChecks(environment: Environment) {
        environment.healthChecks().register("BasicHealthCheck", BasicHealthCheck())
    }

    // This is where I create the database connection. Feel free to just pass the database
    // instance to all of the different routes that need it
    private fun registerDatabase(configuration: ServerConfig, environment: Environment): DBI {
        val factory = DBIFactory()
        return factory.build(environment, configuration.database, "")
    }

    private fun registerRoutes(
        configuration: ServerConfig,
        environment: Environment
    ) {
        environment.run {
            jersey().register(RootRoute())
        }
    }
}