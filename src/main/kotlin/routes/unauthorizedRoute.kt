package com.daccvo.routes

import com.daccvo.domain.model.Endpoint
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*


fun Route.unauthorizedRoute(){
    get(Endpoint.Unauthorized.path){
        call.respond(
            message = "Not Authorized",
            status = HttpStatusCode.Unauthorized
        )
    }
}
