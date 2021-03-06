package de.mc.ladon.server.core.persistence.entities.api

import java.security.Principal

/**
 * User
 * Created by Ralf Ulrich on 05.05.16.
 */
interface User : Principal {

    val password: String?

    val isEnabled: Boolean

    val roles: Set<String>

}
