package amagen.magen.mediastacknews.network

import amagen.magen.mediastacknews.models.Article
import amagen.magen.mediastacknews.models.Categories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//repository of potential requests to the API
class MediaStackRepo {

    companion object{
        // GET request - return list of articles sort by category
        suspend fun getArticlesByCategory(categories: Categories):List<Article>{
            return withContext(Dispatchers.IO){
                MediaStackService.createConnection().articlesByCategory(category = categories.type).data
            }
        }
    }

}