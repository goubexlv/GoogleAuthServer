package com.daccvo.repository

import com.daccvo.domain.model.User

interface UserRepository {
    suspend fun getUserInfo(userId: String) : User?
    suspend fun saveUserInfo(user : User): Boolean
    suspend fun deleteUser(userId : String): Boolean
    suspend fun updateUserInfo(
        userId : String,
        firstName : String,
        lastName : String
    ) : Boolean
}