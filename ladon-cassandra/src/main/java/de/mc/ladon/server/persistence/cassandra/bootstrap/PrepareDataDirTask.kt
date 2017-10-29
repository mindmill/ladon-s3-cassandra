/*
 * Copyright (c) 2016 Mind Consulting UG(haftungsbeschränkt)
 */

package de.mc.ladon.server.persistence.cassandra.bootstrap

import de.mc.ladon.server.core.bootstrap.api.BootstrapTask
import de.mc.ladon.server.core.persistence.DatabaseConfig
import org.apache.velocity.Template
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileWriter
import java.net.InetAddress
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

/**
 * Creates the directories for the embedded cassandra instance.
 */
class PrepareDataDirTask(val config: DatabaseConfig) : BootstrapTask {
    private val LOG = LoggerFactory.getLogger(PrepareDataDirTask::class.java)

    val ladonHome = System.getProperty("ladon.home") ?: "${System.getProperty("user.home")}${File.separator}ladon_data"
    val dataDir = ladonHome + File.separator + "cassandra"
    val configDir = ladonHome + File.separator + "conf"
    val cassandraConfig = configDir + File.separator + "cassandra.yaml"
    val triggersDir = dataDir + File.separator + "triggers"

    override fun shouldRun(): Boolean {
        System.setProperty("cassandra.storagedir", dataDir)
        System.setProperty("cassandra.triggers_dir", triggersDir)
        System.setProperty("ladon.home", ladonHome)
        return !Files.exists(Paths.get(configDir))
    }

    override fun run() {
        Files.createDirectories(Paths.get(triggersDir))
        LOG.info("Created Ladon data directory : $dataDir")
        Files.createDirectories(Paths.get(configDir))
        LOG.info("Created Ladon config directory : $configDir")


        writeCassandraYaml()
        LOG.info("Created Ladon config  : $cassandraConfig")
    }

    private fun writeCassandraYaml() {
        val context = VelocityContext()
        context.put("hostname", InetAddress.getLocalHost().hostName)
        context.put("seeds", config.nodes)

        var template: Template?
        var sw: FileWriter? = null
        try {
            val p = Properties()
            p.setProperty("resource.loader", "class")
            p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader")
            Velocity.init(p)
            template = Velocity.getTemplate("cassandra.yaml")
            sw = FileWriter(Paths.get(cassandraConfig).toFile())
            template!!.merge(context, sw)
        } catch (e: Exception) {
            LOG.error("error while initializing cassandra.yaml")
        } finally {
            sw?.close()
        }
    }


    override fun isFatal(): Boolean {
        return true
    }
}