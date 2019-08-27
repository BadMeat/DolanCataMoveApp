package com.dolan.dolancatamoveapp.detail

import com.google.gson.annotations.SerializedName

data class DetailResponse(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null
)