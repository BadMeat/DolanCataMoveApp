package com.dolan.dolancatamoveapp.detail

import com.google.gson.annotations.SerializedName

data class DetailTvResponse(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("first_air_date")
    val firstAirDate: String? = null
)