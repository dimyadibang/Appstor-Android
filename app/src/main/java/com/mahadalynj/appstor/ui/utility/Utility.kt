package com.mahadalynj.appstor.ui.utility

import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

fun setfullScreen(window: Window){
    WindowCompat.setDecorFitsSystemWindows(window, false)

}

fun lightStatusBar(window: Window, isLight: Boolean = true){
    val wic = WindowInsetsControllerCompat(window,window.decorView)
    wic.isAppearanceLightStatusBars = isLight
}