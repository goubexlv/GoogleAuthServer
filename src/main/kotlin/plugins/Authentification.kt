package com.daccvo.plugins

import com.daccvo.domain.model.Endpoint
import com.daccvo.domain.model.UserSession
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*

fun Application.configureAuth(){
    install(Authentication){
        session<UserSession>(name = "auth-session" ) {
            validate { session ->
                session
            }
            challenge {
                call.respondRedirect(Endpoint.Unauthorized.path)
            }
        }

    }
}