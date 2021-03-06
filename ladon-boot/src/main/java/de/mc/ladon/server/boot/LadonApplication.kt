/*
 * Copyright (c) 2015 Mind Consulting UG(haftungsbeschränkt)
 */

package de.mc.ladon.server.boot

import de.mc.ladon.server.boot.config.DatabaseConfigImpl
import de.mc.ladon.server.boot.config.LadonS3ConfigImpl
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

/**
 * LadonApplication
 * Created by Ralf Ulrich on 27.10.15.
 */
@SpringBootApplication(scanBasePackages = arrayOf("de.mc.ladon.server"))
@EnableConfigurationProperties(DatabaseConfigImpl::class, LadonS3ConfigImpl::class)
open class LadonApplication

fun main(args: Array<String>) {
    SpringApplication.run(LadonApplication::class.java, *args)

}