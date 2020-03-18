package com.example.github_user_search.data.network;


import com.example.github_user_search.data.network.responses.GitHubResponse
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface GitHubApi  {

    @GET("users")
    fun search  (
            @Query("q") login: String
    ) : Observable<GitHubResponse>

    companion object{
        operator fun invoke() : GitHubApi {

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(0, TimeUnit.MILLISECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                    .baseUrl("https://api.github.com/search/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GitHubApi::class.java)
        }
    }

}
