package com.daccvo.routes

import com.daccvo.domain.model.ApiResponse
import com.daccvo.domain.model.Endpoint
import com.daccvo.domain.model.UserSession
import com.daccvo.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteUserRoute(
    app : Application,
    userRepository: UserRepository
){
    authenticate ("auth-session") {

        delete(Endpoint.DeleteUser.path){
            val userSession = call.principal<UserSession>()

            if (userSession == null){
                app.log.info("erreur session")
                call.respondRedirect(Endpoint.Unauthorized.path)
            }else {
                try {
                    val response = userRepository.deleteUser(userSession.id)
                    if(response){
                        app.log.info("suppression reussi")
                        call.respond(
                            message = ApiResponse(
                                success = true,
                                message = "Suppression reussi"
                            )

                        )
                    }else{
                        app.log.info("suppression echoue")
                        call.respond(
                            message = ApiResponse(success = false),
                            status = HttpStatusCode.BadRequest
                        )
                    }

                }catch (e : Exception){
                    app.log.info("Impossible de supprimer : ${e.message}")
                }
            }
        }

    }

}