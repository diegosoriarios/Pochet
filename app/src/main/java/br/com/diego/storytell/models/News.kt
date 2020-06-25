package br.com.diego.storytell.models

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("id")
    var id : Int,
    @SerializedName("created_at")
    var createdAt : String,
    @SerializedName("title")
    var name : String,
    @SerializedName("cover_image")
    var image: String,
    @SerializedName("tags")
    var tag: String,
    @SerializedName("canonical_url")
    var content: String
)