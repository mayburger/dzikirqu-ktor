package com.mayburger.routes

import com.mayburger.models.Prayer
import com.mayburger.models.prayerDataStorage
import com.mayburger.models.prayerStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.litote.kmongo.MongoOperator
import org.litote.kmongo.eq
import org.litote.kmongo.setValue
import java.lang.Exception


fun Application.registerPrayerRoutes() {
    routing {
        prayerRouting()
        prayerDataRouting()
    }
}

fun Route.prayerDataRouting(){
    route("/prayer/data"){
        patch{
            try {
                prayerDataStorage.drop()
                call.respondText("Prayers stored correctly", status = HttpStatusCode.Accepted)
            } catch (e: Exception) {
                call.respondText("Error ${e.message}", status = HttpStatusCode.BadRequest)
            }
        }
        post("/bulk"){
            try {
                val prayers = call.receive<ArrayList<Prayer.PrayerData>>()
                prayerDataStorage.insertMany(prayers)
                call.respondText("Prayers stored correctly", status = HttpStatusCode.Accepted)
            } catch (e: Exception) {
                call.respondText("Error ${e.message}", status = HttpStatusCode.BadRequest)
            }
        }
        patch("/notes"){
            try{
                val prayer = call.receive<Prayer.PrayerData>()
                if (prayer.notes?.isNullOrEmpty() == true){
                    call.respondText("Error, empty notes", status = HttpStatusCode.BadRequest)
                    return@patch
                }
                prayerDataStorage.updateOne(Prayer.PrayerData::id eq prayer.id, setValue(Prayer.PrayerData::notes, prayer.notes))
                call.respondText("Notes updated", status = HttpStatusCode.Accepted)
            }catch (e:Exception){
                call.respondText("Error ${e.message}", status = HttpStatusCode.BadRequest)
            }
        }
        get{
            try{
                call.respond(prayerDataStorage.find().toList())
            }catch (e:Exception){
                call.respondText("Error ${e.message}", status = HttpStatusCode.BadRequest)
            }
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
