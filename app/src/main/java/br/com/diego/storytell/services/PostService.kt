package br.com.diego.storytell.services

import retrofit2.Call
import br.com.diego.storytell.models.Post
import retrofit2.http.GET

interface PostService {
    @GET("posts")
    fun getPosts(): Call<List<Post>>
}