package amagen.magen.mediastacknews.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*
const val FAVORITE_ARTICLES_TABLE = "FAVORITE_ARTICLES_TABLE"

// API response model
data class ArticlesData (
    val pagination:Pagination,
    val data:List<Article>
)

data class Pagination(
    val limit:Int,
    val offset:Int,
    val count:Int,
    val total:Long
)

// entity for room-database
@Entity(tableName = FAVORITE_ARTICLES_TABLE)
data class Article(
    val author:String?,
    @PrimaryKey
    val title:String,
    val description:String,
    @SerializedName("url")
    val articleURL:String,
    @SerializedName("source")
    val articleSource:String,
    val image:String?,
    val category:String,
    val language:String,
    val country:String,
    @SerializedName("published_at")
    val publishDate: String,
    var isFavorite:Boolean?=false
)