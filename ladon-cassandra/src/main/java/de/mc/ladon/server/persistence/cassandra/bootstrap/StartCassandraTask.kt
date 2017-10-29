/*
 * Copyright (c) 2016 Mind Consulting UG(haftungsbeschränkt)
 */

package de.mc.ladon.server.persistence.cassandra.bootstrap

import de.mc.ladon.server.core.bootstrap.api.BootstrapTask
import de.mc.ladon.server.core.exceptions.LadonIllegalArgumentException
import de.mc.ladon.server.core.persistence.Database
import org.apache.cassandra.service.CassandraDaemon
import java.net.Socket
import java.net.SocketException
import java.nio.file.Paths

/**
 * Starts cassandra as embedded database.
 * Checks if there is a running instance before.
 */
class StartCassandraTask(val database: Database) : BootstrapTask {
    override fun shouldRun(): Boolean {
        return try {
            Socket("localhost", 9042).close()
            false
        } catch (e: SocketException) {
            true
        }
    }

    override fun run() {
        val ladonHome = System.getProperty("ladon.home") ?: throw LadonIllegalArgumentException("ladon.home is not set")
        System.setProperty("cassandra.config", Paths.get(ladonHome, "conf", "cassandra.yaml").toUri().toASCIIString())
        CassandraDaemon().apply { init(null); start() }
        database.init()
        database.initSchema()
    }


    override fun isFatal(): Boolean {
        return true
    }
}