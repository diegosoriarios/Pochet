package br.com.diego.bolso

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET

data class Post(
    @SerializedName("id")
    var id : Int,
    @SerializedName("createdAt")
    var createdAt : String,
    @SerializedName("name")
    var name : String,
    @SerializedName("image")
    var image: String,
    @SerializedName("tag")
    var tag: String,
    @SerializedName("content")
    var content: String
)

interface Endpoint {

    @GET("posts")
    fun getPosts() : Call<List<Post>>
}