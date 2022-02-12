package amagen.magen.mediastacknews.ui.favorite

import amagen.magen.mediastacknews.database.ApplicationDB
import amagen.magen.mediastacknews.models.Article
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteArticlesViewModel() : ViewModel() {
    private val _favoriteArticles = MutableLiveData<List<Article>>()
    val favoriteArticles get() = _favoriteArticles


    fun ifAuthenticatedGetFavoriteArticles(applicationDB: ApplicationDB){
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteArticles.postValue(applicationDB.favoriteArticlesTableDao().getFavoriteArticles())
        }
    }
}