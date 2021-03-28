package com.mayburger.routes

import com.mayburger.models.Prayer
import com.mayburger.models.prayerStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.Book
import models.bookStorage
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import org.litote.kmongo.toId
import org.litote.kmongo.util.idValue


fun Application.registerBookRoutes() {
    routing {
        bookRouting()
    }
}

fun Route.bookRouting() {
    route("/book") {
        get {
            call.respond(bookStorage.find().toList())
        }
        delete{
            bookStorage.drop()
        }
        post("/bulk"){
            val books = call.receive<ArrayList<Book>>()
            bookStorage.insertMany(books)
            call.respondText("Books stored correctly", status = HttpStatusCode.Accepted)
        }
        patch{
        }
        delete {
            prayerStorage.drop()
        }
    }
}
