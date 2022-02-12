package amagen.magen.mediastacknews.database

import amagen.magen.mediastacknews.models.Article
import amagen.magen.mediastacknews.models.FAVORITE_ARTICLES_TABLE
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteArticlesDao {

    // insert/update favorite article table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTableForArticle(article:Article)

    // deleting article from favorite table
    @Query("delete from $FAVORITE_ARTICLES_TABLE where title = :title")
    fun deleteArticleFromFavorite(title:String)

    // check if article is set as favorite
    @Query("select isFavorite from $FAVORITE_ARTICLES_TABLE where title = :title")
    fun isFavorite(title:String):Boolean

    // get all favorite articles as list
    @Query("SELECT * FROM $FAVORITE_ARTICLES_TABLE")
    fun getFavoriteArticles():List<Article>



}