package de.mc.ladon.server.core.persistence

/**
 * Configuration for the database
 * Created by Ralf Ulrich on 05.05.16.
 */
interface DatabaseConfig {
    var datacenter: String?

    var rack: String?

    var replicationfactor: String?

    var user: String?

    var password: String?

    var port: Int?

    var nodes: String?
}
