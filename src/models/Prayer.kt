package com.mayburger.models

import com.mayburger.constants.Api
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import kotlin.collections.ArrayList

val database = Api.getClient().getDatabase("dzikirqu") //normal java driver usage
val prayerStorage = database.getCollection<Prayer>() //KMongo extension method
val prayerDataStorage = database.getCollection<Prayer.PrayerData>()

@Serializable
class Prayer(
    @Contextual val id: Id<Prayer> = newId(),
    var book_id: String?= null,
    var title: ArrayList<LanguageString>? = null
)  {
    @Serializable
    class PrayerData(
        @Contextual val id: Id<PrayerData> = newId(),
        var book_id: String?=null,
        var prayer_id:String?=null,
        var order:Int?=null,
        var text:ArrayList<LanguageString>?=null,
        var source: ArrayList<LanguageString>? = null,
        var notes: ArrayList<LanguageString>? = null,
        var audio: String? = null,
        var link: Link? = null
    )

    @Serializable
    class Link(
        val title:ArrayList<LanguageString>?=null,
        val subtitle:ArrayList<LanguageString>?=null,
        val description: String?=null,
        val type: String?=null,
        val link:String?=null
    )

    @Serializable
    class LanguageString(
        var text:String?=null,
        var language:String?=null
    )
}