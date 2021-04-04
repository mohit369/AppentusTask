package com.appentustask.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "offlineList")
class ResponseGetModelItem(
    @SerializedName("author")
    val author: String,
    @SerializedName("download_url")
    val downloadUrl: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    @PrimaryKey
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)