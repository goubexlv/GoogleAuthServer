package com.daccvo.utils
import io.github.cdimascio.dotenv.dotenv

object Constants {
    private val dotenv = dotenv {
        ignoreIfMissing = true
    }

    val AUDIENCE = dotenv["AUDIENCE"] ?: error("AUDIENCE is missing in .env")
    val ISSUER = dotenv["ISSUER"] ?: error("ISSUER is missing in .env")

    val USER = dotenv["USER"] ?: "default_user"
    val PASSWORD = dotenv["PASSWORD"] ?: "default_password"
    val HOST = dotenv["HOST"] ?: "127.0.0.1"
    val PORT = dotenv["PORT"] ?: "27017"
    val MAX_POOL_SIZE = dotenv["MAX_POOL_SIZE"]?.toInt() ?: 20
    val DATABASE_NAME = dotenv["DATABASE_NAME"] ?: "defaultDB"
    val COLLECTION_NAME = dotenv["COLLECTION_NAME"] ?: "defaultCollection"

    val SecretEncryptKey = dotenv["SecretEncryptKey"] ?: error("SecretEncryptKey is missing in .env")
    val SecretAuthKey = dotenv["SecretAuthKey"] ?: error("SecretAuthKey is missing in .env")
}