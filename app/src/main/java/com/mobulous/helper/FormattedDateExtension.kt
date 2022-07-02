package com.mobulous.helper

import java.text.SimpleDateFormat
import java.util.*

fun String.formatDate(dateFormat: String, toFormatDate: String): String?{
    return SimpleDateFormat(toFormatDate, Locale.getDefault()).format(
        SimpleDateFormat(
            dateFormat,
            Locale.getDefault()
        ).parse(this)?:""
    )
}