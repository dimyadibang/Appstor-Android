package com.mahadalynj.appstor.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.LinearLayoutCompat
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.api.ApiClient
import com.mahadalynj.appstor.data.profile.UserRequest
import com.mahadalynj.appstor.data.profile.UserResponse
import com.mahadalynj.appstor.data.profile.helper.Constant
import com.mahadalynj.appstor.data.profile.helper.Constant.Companion.PREF_IS_LOGIN
import com.mahadalynj.appstor.data.profile.helper.Constant.Companion.PREF_IS_TOKEN
import com.mahadalynj.appstor.data.profile.helper.PreferencesHelper
import com.mahadalynj.appstor.ui.main.MainActivity
import com.mahadalynj.appstor.ui.utility.lightStatusBar
import com.mahadalynj.appstor.ui.utility.setfullScreen
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    private lateinit var sessionManager: PreferencesHelper
    lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        PreferencesHelper(this)

        lightStatusBar(window)




        apiClient = ApiClient()
        sessionManager = PreferencesHelper(this)
        initAction()



    }

    override fun onStart(){
        super.onStart()
        if (sessionManager.getBoolean(PREF_IS_LOGIN))
            toMain()
    }

    private fun toMain() {
        Intent(applicationContext, MainActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)

        }

    }

    private fun initAction() {
        btn_login.setOnClickListener {
            login_loading.visibility = View.VISIBLE
            // Validasi
            val username: String = et_username.text?.trim().toString()
            val password: String = et_password.text?.trim().toString()

            // Validasi
            if (username.isEmpty() || password.isEmpty()) {
                login_loading.visibility = View.GONE
                Toast.makeText(this, "Username dan password wajib diisi!", Toast.LENGTH_LONG).show()
            } else {
                apiClient.getApiService(this).login(UserRequest(username, password))
                    .enqueue(object : Callback<UserResponse> {
                        override fun onResponse(
                            call: Call<UserResponse>,
                            response: Response<UserResponse>
                        ) {
                            login_loading.visibility = View.GONE
                            val loginResponse = response.body()


                            if (loginResponse?.token?.isNotEmpty() == true) {
                                sessionManager.put(PREF_IS_TOKEN,response.body()?.token.toString())
                                sessionManager.put(PREF_IS_LOGIN,true)
                                Log.i("token", loginResponse.token.toString().trim())
                                Log.i("id", loginResponse.userId.toString())
                                Log.i("email", loginResponse.email.toString())
                                toMain()
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "Username dan password tidak sesuai",
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.e("token", loginResponse?.token.toString().trim())
                                Log.e("id", loginResponse?.userId.toString())
                                Log.e("email", loginResponse?.email.toString())

                            }
                        }

                        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                            login_loading.visibility = View.GONE
                            Toast.makeText(
                                applicationContext,
                                "Gagal kontak server",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
            }
        }
    }
}
