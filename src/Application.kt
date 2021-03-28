package com.mayburger

import com.mayburger.routes.registerBookRoutes
import com.mayburger.routes.registerPrayerRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.serializersModuleOf
import org.litote.kmongo.id.serialization.IdKotlinXSerializationModule

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            serializersModule = IdKotlinXSerializationModule
        })
    }
    registerPrayerRoutes()
    registerBookRoutes()
}

