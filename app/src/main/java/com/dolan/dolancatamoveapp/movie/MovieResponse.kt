package com.dolan.dolancatamoveapp.movie

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("results")
    val results: List<Movie?>? = null
)