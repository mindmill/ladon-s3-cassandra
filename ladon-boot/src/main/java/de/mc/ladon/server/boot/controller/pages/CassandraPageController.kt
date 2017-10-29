package de.mc.ladon.server.boot.controller.pages

import de.mc.ladon.server.boot.controller.FrameController
import de.mc.ladon.server.core.persistence.Database
import de.mc.ladon.server.persistence.cassandra.dao.api.SystemInfoDAO
import de.mc.ladon.server.persistence.cassandra.database.DatabaseImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * Controller for the cassandra info page
 * Created by Ralf Ulrich on 13.12.15.
 */
@Controller
class CassandraPageController : FrameController() {

    @Autowired
    lateinit var systemDao: SystemInfoDAO
    @Autowired
    lateinit var database: Database


    @RequestMapping("cassandra")
    fun cassandra(model: MutableMap<String, Any>, @RequestParam repoid: String): String {
        model.put("local", systemDao.getLocalInformation())
        model.put("peers", systemDao.getPeerInformation())
        try {

            model.put("hosts", (database as DatabaseImpl).clusterMetadata.allHosts)
            model.put("clustername", (database as DatabaseImpl).clusterMetadata.clusterName)
        } catch (e: IllegalStateException) {
            model.flashDanger(e.message ?: "ALL CASSANDRA HOSTS ARE DOWN")
        }
        return super.updateModel(model, "cassandra", repoid)
    }

}
