package amagen.magen.mediastacknews.ui.articles.allCategories

import amagen.magen.mediastacknews.models.Article
import amagen.magen.mediastacknews.models.Categories
import amagen.magen.mediastacknews.network.MediaStackRepo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ArticlesViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles get()=_articles

    private val _UIArticlesByCategory = MutableLiveData<List<Article>>()
    val UIArticlesByCategory get() = _UIArticlesByCategory

    val UIArticleListWithImages = ArrayList<Article>()
    val listOfAllCategories = ArrayList<List<Article>>()

    init {
        getEachCategoryArticle()
    }

    private fun getEachCategoryArticle(){
        // run on the IO thread
        viewModelScope.launch(Dispatchers.IO){
            // API has limit of monthly requests
            // using try and catch blocks to avoid app crashes
            try{
                Categories.values().forEach {
                    //get list of each category
                    val currentCategory = MediaStackRepo.getArticlesByCategory(it)
                    //find in each list the first article with image
                    UIArticleListWithImages.add(getFirstArticleWithImage(currentCategory))
                    //use the loop to archive the list of given articles
                    listOfAllCategories.add(currentCategory)
                }
                _UIArticlesByCategory.postValue(UIArticleListWithImages)
            }catch (e:HttpException){
                // potentially block to handle HttpException -> may cause after too many requests
                e.printStackTrace()
            }
            catch (e:Exception){
                // potentially block to handle any other exception
                e.printStackTrace()
            }
        }
    }

    //uses the recycle adapter position to get the list of articles in category
    fun getArticlesByCategory(category:Int):List<Article>{
        return listOfAllCategories[category]
    }

    // find article with image -> this function used to init the home screen
    private fun getFirstArticleWithImage(currentCategory: List<Article>): Article {
        for(article in currentCategory){
            if(!article.image.isNullOrEmpty()||article.image.toString() != "null"){
                return article
            }
        }
        //if there's no picture in the current category, return the first article
        return currentCategory[0]
    }

}