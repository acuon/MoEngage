package com.acuon.moengage.utils.extensions

import com.acuon.moengage.common.Constants
import java.text.SimpleDateFormat
import java.util.Locale

fun String?.serverToPrettyDate(
    currentFormat: String,
    targetFormat: String = Constants.DateFormats.APP_DATE_FORMAT
): String {
    if (this.isNullOrEmpty()) {
        return ""
    }

    val inputFormat = SimpleDateFormat(currentFormat, Locale.getDefault())
    val outputFormat = SimpleDateFormat(targetFormat, Locale.getDefault())

    return try {
        val date = inputFormat.parse(this)
        outputFormat.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}