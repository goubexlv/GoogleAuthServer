package com.daccvo.routes

import com.daccvo.domain.model.Endpoint
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.rootRoute(){
    get(Endpoint.Root.path){
        call.respondText("Bienvenue sur mon API Server")
    }
}