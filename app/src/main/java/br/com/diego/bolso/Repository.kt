package br.com.diego.bolso

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET

data class Repository(
    @SerializedName("id")
    var id : Int,
    @SerializedName("name")
    var name : String,
    @SerializedName("language")
    var language : String,
    @SerializedName("image")
    var image: String
)

interface Endpoint {

    @GET("repos")
    fun getPosts() : Call<List<Repository>>
}