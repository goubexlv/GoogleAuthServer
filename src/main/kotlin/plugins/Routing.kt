package com.daccvo.plugins

import com.daccvo.repository.UserRepository
import com.daccvo.routes.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    routing {
        val userRepository : UserRepository by application.inject()
        rootRoute()
        getUserInfoRoute(userRepository)
        updateUserInfoRoute(userRepository)
        deleteUserRoute(userRepository)
        signOutRoute()
        authorizedRoute()
        unauthorizedRoute()
        tokenVerificationRoute(userRepository)
    }
}
