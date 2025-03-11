package com.daccvo.routes

import com.daccvo.domain.model.ApiResponse
import com.daccvo.domain.model.Endpoint
import com.daccvo.domain.model.UserSession
import com.daccvo.domain.model.UserUpdate
import com.daccvo.repository.UserRepository
import com.daccvo.utils.Logs
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.updateUserInfoRoute(
    userRepository: UserRepository
){

    authenticate ("auth-session"){
        put(Endpoint.UpdateUserInfo.path){
            val userSession = call.principal<UserSession>()
            val userUpdate = call.receive<UserUpdate>()
            if (userSession == null){
                Logs.logs("erreur de session")
                call.respondRedirect(Endpoint.Unauthorized.path)
            }else{
                try {
                    call.sessions.clear<UserSession>()
                    val response = userRepository.updateUserInfo(
                        userSession.id,
                        userUpdate.firstName,
                        userUpdate.lastName
                    )

                    if (response){
                        Logs.logs("update reussi")
                        call.respond(
                            message = ApiResponse(
                                success = true,
                                message = "update reussi"
                            ),
                            status = HttpStatusCode.OK
                        )
                    }else{
                        Logs.logs("erreur update")
                        call.respond(
                            message = ApiResponse(success = false),
                            status = HttpStatusCode.BadRequest
                        )
                    }

                }catch (e : Exception) {
                    Logs.logs("Impossible d'update : ${e.message}")
                }

            }
        }
    }

}