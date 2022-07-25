package com.mahadalynj.appstor.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.ui.login.LoginActivity
import com.mahadalynj.appstor.ui.main.MainActivity
import com.mahadalynj.appstor.ui.utility.lightStatusBar
import com.mahadalynj.appstor.ui.utility.setfullScreen

@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       lightStatusBar(window)
        setfullScreen(window)
        /*      setTheme(R.style.SplashscreenThema_)
           //  setContentView(R.layout.activity_splash_screen)

     */





        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }, 2000)
    }
}