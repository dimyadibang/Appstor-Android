package com.mahadalynj.appstor.ui.utility

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.view.View
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.snackbar.Snackbar

fun setfullScreen(window: Window){
    WindowCompat.setDecorFitsSystemWindows(window, false)

}

fun lightStatusBar(window: Window, isLight: Boolean = true){
    val wic = WindowInsetsControllerCompat(window,window.decorView)
    wic.isAppearanceLightStatusBars = isLight
}

fun View.displaySnackbar(messae : String) {
    Snackbar.make(this, messae, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("OK") {
            snackbar.dismiss()
        }
    }.show()
}

fun ContentResolver.getFileName(imgUri : Uri) : String {
    var name = ""
    val cursor = query(imgUri, null, null, null, null)
    if (cursor != null) {
        val colIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor.moveToFirst()
        name = cursor.getString(colIndex)
    }
    return name
}