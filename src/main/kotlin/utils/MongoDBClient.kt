package com.daccvo.utils

import com.daccvo.utils.Constants.COLLECTION_NAME
import com.daccvo.utils.Constants.DATABASE_NAME
import com.daccvo.utils.Constants.HOST
import com.daccvo.utils.Constants.MAX_POOL_SIZE
import com.daccvo.utils.Constants.PASSWORD
import com.daccvo.utils.Constants.PORT
import com.daccvo.utils.Constants.USER
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

object MongoDBClient {

    private val database: MongoDatabase
    val usersCollection: MongoCollection<Document>

    init {
        val credentials = if (USER.isNotBlank() && PASSWORD.isNotBlank()) {
            "$USER:$PASSWORD@"
        } else {
            ""
        }

        val uri = "mongodb://$credentials$HOST:$PORT/?maxPoolSize=$MAX_POOL_SIZE&w=majority"
        val mongoClient = MongoClients.create(uri)
        database = mongoClient.getDatabase(DATABASE_NAME)
        usersCollection = database.getCollection(COLLECTION_NAME)
    }
}