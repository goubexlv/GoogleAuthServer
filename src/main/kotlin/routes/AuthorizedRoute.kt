package com.daccvo.routes

import com.daccvo.domain.model.ApiResponse
import com.daccvo.domain.model.Endpoint
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authorizedRoute(){
    authenticate("auth-session"){
        get(Endpoint.Authorized.path){
            call.respond(
                message = ApiResponse(success = true),
                status = HttpStatusCode.OK
            )
        }
    }



}