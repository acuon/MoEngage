package com.acuon.moengage.utils.dialogs

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.acuon.moengage.R

fun showPermissionNecessaryDialog(
    context: Context, message: String, openSettings: Boolean = true, callback: () -> Unit = {}
) {
    val positiveButtonText = if (openSettings) context.getString(R.string.open_settings)
    else context.getString(R.string.grant_permissions)

    val alertDialog = AlertDialog.Builder(context).also {
        it.setMessage(message).setCancelable(false)
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                dialog.dismiss()
                if (openSettings) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.data = uri
                    context.startActivity(intent)
                } else {
                    callback.invoke()
                }
            }
            .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
    }
    alertDialog.show()
}
