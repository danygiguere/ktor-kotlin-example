package com.blog.config

import com.blog.models.*
import io.github.cdimascio.dotenv.Dotenv
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object Database {
    fun init() {
//        uncomment the lines below to use the in-memory db (and comment the mysql driver lines).
//        val driverClassName = "org.h2.Driver"
//        val jdbcURL = "jdbc:h2:file:./build/db"
//        val database = Database.connect(jdbcURL, driverClassName)

        val dotenv = Dotenv.load();
        val dbHost = dotenv.get("DB_HOST")
        val dbPort = dotenv.get("DB_PORT")
        val dbName = dotenv.get("DB_NAME")
        val dbUser = dotenv.get("DB_USER")
        val dbPassword = dotenv.get("DB_PASSWORD")
        val database = Database.connect("${dbHost}:${dbPort}/${dbName}", driver = "com.mysql.cj.jdbc.Driver",
            user = dbUser, password = dbPassword)

        transaction(database) {
            SchemaUtils.create(Users)
            SchemaUtils.create(Posts)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}