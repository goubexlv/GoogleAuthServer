package com.daccvo.utils

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase

fun connectUserDB(
    user: String = "admin",
    password: String = "password",
    host: String = "127.0.0.1",
    port: String = "27017",
    maxPoolSize: Int = 20,
    databaseName: String = "googleAuthServer"
): MongoDatabase {
    // Construire les informations d'identification
    val credentials = if (user.isNotBlank() && password.isNotBlank()) {
        "$user:$password@"
    } else {
        ""
    }

    // Construire l'URI de connexion
    val uri = "mongodb://$credentials$host:$port/?maxPoolSize=$maxPoolSize&w=majority"

    // Créer une instance du client MongoDB
    val mongoClient = MongoClients.create(uri)

    // Retourner la base de données spécifiée
    return mongoClient.getDatabase(databaseName)
}