package br.com.diego.storytell.services

import br.com.diego.storytell.models.News
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @GET("articles")
    fun getNews(): Call<List<News>>
}