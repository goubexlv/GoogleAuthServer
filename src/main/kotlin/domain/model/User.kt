package com.daccvo.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.Document

@Serializable
data class User(
    val id : String,
    val name : String,
    val emailAdress : String,
    val profilePhoto : String
){
    fun toDocument(): Document = Document.parse(Json.encodeToString(this))

    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        fun fromDocument(document: Document): User = json.decodeFromString(document.toJson())
    }
}
