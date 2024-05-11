package com.acuon.moengage.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.acuon.moengage.utils.extensions.showToast

fun Context.shareArticle(url: String?) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"

    shareIntent.putExtra(Intent.EXTRA_TEXT, url)

    try {
        startActivity(Intent.createChooser(shareIntent, "Share with"))
    } catch (e: PackageManager.NameNotFoundException) {
        showToast("No app to handle this action")
    }

}