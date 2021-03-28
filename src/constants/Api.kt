package com.mayburger.constants

import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object Api {
    const val BASE_API = "mongodb://127.0.0.1:27017"

    fun getClient():CoroutineClient{
        return KMongo.createClient(BASE_API).coroutine
    }
}