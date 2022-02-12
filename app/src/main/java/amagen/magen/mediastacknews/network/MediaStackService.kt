package amagen.magen.mediastacknews.network

import amagen.magen.mediastacknews.BuildConfig
import amagen.magen.mediastacknews.models.Article
import amagen.magen.mediastacknews.models.ArticlesData
import amagen.magen.mediastacknews.models.Categories
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = BuildConfig.MEDIASTACK_BASE_URL

interface MediaStackService {
//072c0018c423c2bf05f75f6c5cf02b05

    //init the get request
    @GET("news")
    suspend fun articlesByCategory(
        @Query("access_key") apiKey:String=BuildConfig.MEDIASTACK_API_KEY,
        @Query("categories") category:String=Categories.SPORTS.type,
        @Query("languages") lan:String="en"
    ):ArticlesData



    //init retrofit
    companion object{
        fun createConnection():MediaStackService{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(MediaStackService::class.java)
        }
    }
}