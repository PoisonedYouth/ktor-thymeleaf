package com.poisonedyouth.plugins

import io.ktor.server.application.Application
import com.poisonedyouth.AddressTable
import com.poisonedyouth.CustomerTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


fun Application.createDatabaseConnection() {
    Database.connect(hikari())

    transaction {
        SchemaUtils.createMissingTablesAndColumns(AddressTable, CustomerTable)
    }
}

private fun hikari(): HikariDataSource {
    val config = HikariConfig()
    config.driverClassName = "org.h2.Driver"
    config.jdbcUrl = "jdbc:h2:file:./db"
    config.username = "root"
    config.password = "password"
    config.maximumPoolSize = 3
    config.isAutoCommit = false
    config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    config.validate()
    return HikariDataSource(config)
}