package com.mahadalynj.appstor.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.api.ApiClient
import com.mahadalynj.appstor.data.profile.helper.Constant
import com.mahadalynj.appstor.data.profile.helper.Constant.Companion.PREF_IS_TOKEN
import com.mahadalynj.appstor.data.profile.helper.PreferencesHelper
import com.mahadalynj.appstor.ui.login.LoginActivity
import com.mahadalynj.appstor.ui.utility.lightStatusBar
import com.mahadalynj.appstor.ui.utility.setfullScreen
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {


    lateinit var sharedPreferences : PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        lightStatusBar(window)
        setfullScreen(window)

        sharedPreferences = PreferencesHelper(this)

        id_nama_user.text = sharedPreferences.getString(Constant.PREF_IS_ID)
        id_email.text = sharedPreferences.getString(Constant.PREF_IS_EMAIL)
        id_token.text = sharedPreferences.getString(Constant.PREF_IS_TOKEN)
        id_token2.text = sharedPreferences.getString(PREF_IS_TOKEN)
        btn_logout.setOnClickListener {
            sharedPreferences.clear()
          Intent(this, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                Toast.makeText(applicationContext, "Berhasil LogOut", Toast.LENGTH_SHORT).show()

            }
        }

    }
}