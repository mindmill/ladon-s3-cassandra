/*
 * Copyright (c) 2015 Mind Consulting UG(haftungsbeschränkt)
 */

package de.mc.ladon.server.core.tasks.api

import de.mc.ladon.server.core.request.LadonCallContext

/**
 * RepositoryTask
 * Created by Ralf Ulrich on 30.04.15.
 */
interface RepositoryTask<out T> {

    fun run(callContext: LadonCallContext): T
}


