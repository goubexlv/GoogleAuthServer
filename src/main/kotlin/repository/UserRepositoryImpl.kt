package com.daccvo.repository

import com.daccvo.domain.model.User
import com.daccvo.utils.MongoDBClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import org.bson.Document
import org.bson.types.ObjectId

class UserRepositoryImpl : UserRepository {



    override suspend fun getUserInfo(userId: String): User? {
        val user = MongoDBClient.usersCollection.find(Filters.eq("id", userId)).firstOrNull()
        if (user == null) {
            return null
        }
        return User(
            id = user["_id"].toString(),
            name = user["name"]?.toString() ?: "",
            emailAdress = user["emailAdress"]?.toString() ?: "",
            profilePhoto = user["profilePhoto"]?.toString() ?: ""
        )

    }

    override suspend fun saveUserInfo(user: User): Boolean {
        val userExisting = MongoDBClient.usersCollection.find(Filters.eq("id", user.id)).firstOrNull()

        if (userExisting != null) {
            return true
        }

        val doc = user.toDocument()
        return MongoDBClient.usersCollection.insertOne(doc).wasAcknowledged()

    }

    override suspend fun deleteUser(userId: String): Boolean {
        return MongoDBClient.usersCollection.deleteOne(Filters.eq("id", userId)).wasAcknowledged()
    }

    override suspend fun updateUserInfo(userId: String, firstName: String, lastName: String): Boolean {
        val update = Updates.combine(Updates.set(User::name.toString(),"$firstName $lastName"))
        return MongoDBClient.usersCollection.updateOne(Filters.eq("id", userId), update).wasAcknowledged()

    }
}