package br.com.diego.storytell.models

import com.google.gson.annotations.SerializedName

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