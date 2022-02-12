package amagen.magen.mediastacknews

import amagen.magen.mediastacknews.database.ApplicationDB
import amagen.magen.mediastacknews.models.Article
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// application view model -> repository of general functions
class MainActivityViewModel(application: Application):AndroidViewModel(application) {

    // create connection to application database.
    val applicationDB = ApplicationDB.create(application)


    //check if the article is in favorite table
    suspend fun checkIfArticleIsFavorite(title:String):Boolean{
        return withContext(Dispatchers.IO) {
            applicationDB.favoriteArticlesTableDao().isFavorite(title)
        }
    }

    private fun addFavoriteArticle(article: Article){
        viewModelScope.launch(Dispatchers.IO) {
            applicationDB.favoriteArticlesTableDao().upsertTableForArticle(article)
        }
    }
    private fun deleteArticleFromFavoritesTable(title:String){
        viewModelScope.launch(Dispatchers.IO) {
            applicationDB.favoriteArticlesTableDao().deleteArticleFromFavorite(title)
        }
    }
    // add/remove article to favorite articles table
    fun addOrRemoveFromDB(article: Article, isSelected: Boolean) {
        article.isFavorite = isSelected
        if (isSelected) {
            addFavoriteArticle(article)
        } else {
            deleteArticleFromFavoritesTable(article.title)
        }
    }







}