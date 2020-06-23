package br.com.diego.storytell.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostFactory {
    private const val BASE_URL = "https://5c2fd57e8b95c100141e7427.mockapi.io/"

    fun makePostService(): PostService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PostService::class.java)
}