package basicserver

import io.dropwizard.Configuration
import io.dropwizard.db.DataSourceFactory

class ServerConfig(val name: String = "unknown",
                   val database: DataSourceFactory
) : Configuration()