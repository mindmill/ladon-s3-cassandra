package de.mc.ladon.server.boot.config

import de.mc.ladon.server.core.bootstrap.api.BootstrapRunner
import de.mc.ladon.server.core.bootstrap.impl.CreateAdminUserTask
import de.mc.ladon.server.core.persistence.Database
import de.mc.ladon.server.core.persistence.DatabaseConfig
import de.mc.ladon.server.core.persistence.services.api.LadonUserDetailsManager
import de.mc.ladon.server.persistence.cassandra.bootstrap.PrepareDataDirTask
import de.mc.ladon.server.persistence.cassandra.bootstrap.StartCassandraTask
import org.springframework.beans.factory.SmartInitializingSingleton
import org.springframework.beans.factory.annotation.Value
import javax.inject.Inject
import javax.inject.Named

/**
 * @author Ralf Ulrich
 * *         24.10.16
 */
@Named
open class BootstrapRunnerBean @Inject constructor(val runner: BootstrapRunner,
                                                   val database: Database,
                                                   val userdetailsManager: LadonUserDetailsManager,
                                                   val config: DatabaseConfig) : SmartInitializingSingleton {

    @Value("\${ladon.db.init:false}")
    lateinit var initSchema: String


    override fun afterSingletonsInstantiated() {
        runner.run(PrepareDataDirTask(config))
        runner.run(StartCassandraTask(database))
        runner.run(CreateAdminUserTask(userdetailsManager))
    }
}
