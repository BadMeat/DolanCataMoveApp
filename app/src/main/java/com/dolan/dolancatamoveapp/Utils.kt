package com.dolan.dolancatamoveapp

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun convertDate(date: String?): String? {
    val df = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val mDate = inputFormat.parse(date)
    return df.format(mDate)
}