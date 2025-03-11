package com.daccvo.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Logs {
    private val logger: Logger = LoggerFactory.getLogger(Logs::class.java)

    fun logs(message: String) {
        logger.info(message)
    }
}