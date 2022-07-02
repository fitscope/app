package com.mobulous.helper

import android.content.Context

fun Int.dpToPix(con: Context): Int {
    val density: Float = con.getResources()
        .getDisplayMetrics().density
    return Math.round(this.toFloat() * density)
}