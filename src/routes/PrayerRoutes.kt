package com.mayburger.routes

import com.mayburger.models.Prayer
import com.mayburger.models.prayerDataStorage
import com.mayburger.models.prayerStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.litote.kmongo.eq
import java.lang.Exception


fun Application.registerPrayerRoutes() {
    routing {
        prayerRouting()
        prayerDataRouting()
    }
}

fun Route.prayerDataRouting(){
    route("/prayer/data"){
        post("/bulk"){
            try {
                val prayers = call.receive<ArrayList<Prayer.PrayerData>>()
                prayerDataStorage.insertMany(prayers)
                call.respondText("Prayers stored correctly", status = HttpStatusCode.Accepted)
            } catch (e: Exception) {
                call.respondText("Error ${e.message}", status = HttpStatusCode.BadRequest)
            }
        }
        get{
            call.respond(prayerDataStorage.find().toList())
        }
        get("/{prayer_id}"){
            val id = call.parameters["prayer_id"]
            prayerDataStorage.find(Prayer.PrayerData::prayer_id eq id).toList().let {
                if (it.isNotEmpty()){
                    call.respond(it)
                }
            }
        }
        get("/count"){
            call.respond(prayerDataStorage.countDocuments())
        }
    }
}

fun Route.prayerRouting() {
    route("/prayer") {
        get {
            call.respond(prayerStorage.find().toList())
        }
        get("/{id}"){
            val id = call.parameters["id"]
            prayerStorage.find(Prayer::book_id eq id).toList().let {
                if (it.isNotEmpty()){
                    call.respond(it)
                }
            }
        }
        delete {
            prayerStorage.drop()
        }
        post {
            val prayer = call.receive<Prayer>()
            prayerStorage.insertOne(prayer)
            call.respondText("Prayer stored correctly", status = HttpStatusCode.Accepted)
        }
        post("/bulk") {
            try {
                val prayers = call.receive<ArrayList<Prayer>>()
                prayerStorage.insertMany(prayers)
                call.respondText("Prayers stored correctly", status = HttpStatusCode.Accepted)
            } catch (e: Exception) {
                call.respondText("Error ${e.message}", status = HttpStatusCode.BadRequest)
            }
        }
        patch {
            val prayer = call.receive<Prayer>()
            call.respond(prayerStorage.find(Prayer::id eq prayer.id).toList())
        }
        delete {
            prayerStorage.drop()
        }
    }
}
