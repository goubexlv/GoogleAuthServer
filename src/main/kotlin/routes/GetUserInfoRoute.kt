package com.daccvo.routes

import com.daccvo.domain.model.ApiResponse
import com.daccvo.domain.model.Endpoint
import com.daccvo.domain.model.UserSession
import com.daccvo.repository.UserRepository
import com.daccvo.utils.Logs
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getUserInfoRoute(
    userRepository: UserRepository
){
    authenticate ("auth-session") {
        get(Endpoint.GetUserInfo.path){
            val userSession = call.principal<UserSession>()
            if(userSession == null){
                Logs.logs("session invalide")
                call.respondRedirect(Endpoint.Unauthorized.path)
            }else {
                try{
                    call.respond(
                        message = ApiResponse(
                            success = true,
                            user = userRepository.getUserInfo(userSession.id)
                        ),
                        status = HttpStatusCode.OK
                    )

                } catch (e : Exception) {
                    Logs.logs("Erreur lors de la recuperation : ${e.message}")
                    call.respondRedirect(Endpoint.Unauthorized.path)
                }
            }

        }
    }



}