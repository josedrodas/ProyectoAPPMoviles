package com.example.app_joserodas.model

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class OpenLibraryResponse(
    val docs: List<OpenLibraryDoc>?
)

data class OpenLibraryDoc(
    val key: String,
    val title: String,
    @SerializedName("author_name")
    val authorName: List<String>?,
    @SerializedName("cover_i")
    val coverId: Int?,
    val publisher: List<String>?,
    @SerializedName("first_sentence")
    val firstSentence: List<String>?
)

data class WorkDetailResponse(
    val title: String,
    val description: JsonElement?
)