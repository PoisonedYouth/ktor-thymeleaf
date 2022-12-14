package com.poisonedyouth

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.poisonedyouth.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        createDatabaseConnection()
        configureTemplating()
        configureContentNegotiation()
    }.start(wait = true)
}
