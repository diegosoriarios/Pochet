package br.com.diego.storytell.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsFactory {
    private const val BASE_URL = "https://dev.to/api/"

    fun makeNewsService(): NewsService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsService::class.java)
}