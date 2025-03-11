package com.daccvo.plugins

import com.daccvo.domain.model.UserSession
import com.daccvo.utils.Constants.SecretAuthKey
import com.daccvo.utils.Constants.SecretEncryptKey
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import kotlinx.serialization.Serializable
import java.io.File
import kotlin.time.Duration.Companion.minutes

fun Application.configureSecurity() {
    install(Sessions) {
        val secretEncryptKey = hex(SecretEncryptKey)
        val secretAuthKey = hex(SecretAuthKey)

        cookie<UserSession>(
            name = "USER_SESSION",
            storage = directorySessionStorage(File(".sessions"))
        ) {
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretAuthKey))
        }
    }

}

