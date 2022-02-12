package amagen.magen.mediastacknews.database

import amagen.magen.mediastacknews.models.Article
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DB_VERSION =1
const val DB_NAME="ApplicationDB"


@Database(entities = [Article::class], version = DB_VERSION)
abstract class ApplicationDB: RoomDatabase() {
    // set application database to save favortie articles
    companion object{
        fun create(context: Context):ApplicationDB= Room
            .databaseBuilder(context, ApplicationDB::class.java, DB_NAME)
            .build()
    }
    // database DAO
    abstract fun favoriteArticlesTableDao():FavoriteArticlesDao

}