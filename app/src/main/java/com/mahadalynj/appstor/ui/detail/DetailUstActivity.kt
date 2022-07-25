package com.mahadalynj.appstor.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.api.ApiClient
import com.mahadalynj.appstor.ui.utility.lightStatusBar
import com.mahadalynj.appstor.ui.utility.setfullScreen
import kotlinx.android.synthetic.main.activity_detail_ust.*

class DetailUstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_ust)
        val intentName = intent.getStringExtra("intent_name")
        val intentEmail = intent.getStringExtra("intent_email")
        val intentPhone = intent.getStringExtra("intent_phone")
        val intentImage = intent.getStringExtra("intent_image")

        lightStatusBar(window)
        setfullScreen(window)

        Glide.with(this)
            .load(intentImage )
            .placeholder(R.drawable.img_fotokosong)
            .error(R.drawable.img_fotokosong)
            .into(image_photo_ust)
        nama_ust_detail.text = intentName
        email_ust_detail.text = intentEmail
        phone_ust_detail.text = intentPhone
    }

}