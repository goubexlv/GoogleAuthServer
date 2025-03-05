package com.daccvo.routes

import com.daccvo.domain.model.ApiRequest
import com.daccvo.domain.model.Endpoint
import com.daccvo.domain.model.User
import com.daccvo.domain.model.UserSession
import com.daccvo.repository.UserRepository
import com.daccvo.utils.Constants.AUDIENCE
import com.daccvo.utils.Constants.ISSUER
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.pipeline.*

fun Route.tokenVerificationRoute(
    app : Application,
    userRepository: UserRepository
){

    post(Endpoint.TokenVerification.path){
        val request = call.receive<ApiRequest>()
        if(request.tokenId.isNotEmpty()) {
            val result = verifyGoogleTokenId(tokenId = request.tokenId)
            if (result != null){
                val sub = result.payload["sub"].toString()
                val name = result.payload["name"].toString()
                val emailAddress = result.payload["email"].toString()
                val profilePhoto = result.payload["picture"].toString()
                val user = User(
                    id = sub,
                    name = name,
                    emailAdress = emailAddress,
                    profilePhoto = profilePhoto
                )

                val response = userRepository.saveUserInfo(user)
                if (response) {
                    app.log.info("Verification du token reussi : $name, $emailAddress")
                    call.sessions.set(UserSession(id = sub , name = name ))
                    call.respondRedirect(Endpoint.Authorized.path)
                } else {
                    call.respondRedirect(Endpoint.Unauthorized.path)
                }
            } else {
                app.log.info("Verification du token echoue")
                call.respondRedirect(Endpoint.Unauthorized.path)
            }
        }else {
            app.log.info("Token non specifier")
            call.respondRedirect(Endpoint.Unauthorized.path)
        }

    }

}



fun verifyGoogleTokenId(tokenId : String) : GoogleIdToken? {
    return  try{
        val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
            .setAudience(listOf(AUDIENCE))
            .setIssuer(ISSUER)
            .build()
        verifier.verify(tokenId)
    }catch (e : Exception){
        return null
    }

}