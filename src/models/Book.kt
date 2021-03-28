package models

import com.mayburger.constants.Api
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*
import kotlin.collections.ArrayList

val database = Api.getClient().getDatabase("dzikirqu") //normal java driver usage
val bookStorage = database.getCollection<Book>() //KMongo extension method

@Serializable
class Book(
    @Contextual val id: Id<Book> = newId(),
    var title: ArrayList<LanguageString>? = ArrayList(),
    var type: String? = "prayer"
)  {
    @Serializable
    class LanguageString(
        var text:String?=null,
        var language:String?=null
    )
}