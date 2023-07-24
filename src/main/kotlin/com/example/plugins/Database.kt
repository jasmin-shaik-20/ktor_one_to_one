package com.example.plugins

import com.example.dao.Profile
import com.example.dao.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

fun configureDatabase() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/userdata",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "Jasmin@20"
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(Users, Profile)


    }

}

suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }