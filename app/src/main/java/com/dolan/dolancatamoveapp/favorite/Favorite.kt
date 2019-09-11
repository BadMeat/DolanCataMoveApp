package com.dolan.dolancatamoveapp.favorite

data class Favorite(
    val id: Long?,
    val name: String?,
    val rate: Double?,
    val detail: String?,
    val poster: String?,
    val type: Int?,
    val release : String?
) {
    companion object {
        const val TABLE_NAME = "DOLAN_FAVORITE"
        const val FAV_ID = "FAV_ID"
        const val FAV_NAME = "FAV_NAME"
        const val FAV_RATE = "FAV_RATE"
        const val FAV_DETAIL = "FAV_DETAIL"
        const val FAV_POSTER = "FAV_POSTER"
        const val FAV_TYPE = "FAV_TYPE"
        const val FAV_RELEASE = "FAV_RELEASE"
    }
}