package com.mahadalynj.appstor.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mahadalynj.appstor.api.ApiClient
import com.mahadalynj.appstor.data.profile.UserModel
import com.mahadalynj.appstor.data.profile.UserUpdate
import com.mahadalynj.appstor.data.profile.helper.Constant
import com.mahadalynj.appstor.data.profile.helper.PreferencesHelper
import com.mahadalynj.appstor.databinding.ActivityProfileBinding
import com.mahadalynj.appstor.ui.login.LoginActivity
import com.mahadalynj.appstor.ui.utility.lightStatusBar
import com.mahadalynj.appstor.ui.utility.setfullScreen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileActivity : AppCompatActivity(){
    private lateinit var binding: ActivityProfileBinding

    private lateinit var apiClient: ApiClient
    lateinit var sharedPreferences : PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiClient = ApiClient()
        lightStatusBar(window)
        setfullScreen(window)

        sharedPreferences = PreferencesHelper(this)

        getUstadUser()

        binding.btnLogout.setOnClickListener {
            sharedPreferences.clear()
          Intent(this, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                Toast.makeText(applicationContext, "Berhasil LogOut", Toast.LENGTH_SHORT).show()
            }
        }

        initAction()
    }


    private fun initAction(){
        binding.saveUpdateUser.setOnClickListener {
            editUser()
        }

    }

    private fun editUser() {
        val idUstadUstad = sharedPreferences.getString(Constant.ID_USTAD_USER)
        val update = UserUpdate()

        update.name = binding.idNamaUser.text.toString().trim()
        update.email = binding.idEmail.text.trim().toString()
        update.phone = binding.idTlpnUser.text.trim().toString()

        apiClient.getApiService(this).editustaduser(idUstadUstad.toString().toInt(),update)
            .enqueue((object : Callback<UserModel> {
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                     if ( response.isSuccessful) {
                        Log.i("?", response.toString())
                        Toast.makeText(applicationContext, "Sudah ter-update", Toast.LENGTH_SHORT).show()

                    }else {
                         Toast.makeText(
                             applicationContext,
                             "gagal update",
                             Toast.LENGTH_LONG
                         ).show()
                         Log.i("?else", response.toString())
                     }
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    val toast = Toast.makeText(applicationContext, "Gagal", Toast.LENGTH_SHORT)
                    toast.show()
                    Log.e("?", t.message.toString())
                }
            }
                    )
            )
    }

    private fun getUstadUser(){

        val user = sharedPreferences.getString(Constant.PREF_IS_ID)
        val parameters= HashMap<String, String>()
        parameters["user"] = "$user"
        apiClient.getApiService(this).dataustadzuser(parameters)
            .enqueue(object : Callback<UserModel> {
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    val data = response.body()?.datauser?.let { ArrayList(it) }
                    val a = data?.get(0)

                    Log.e("a", a?.id.toString())
                    sharedPreferences.put(Constant.ID_USTAD_USER,a?.id.toString())
                    val name = a?.name.toString()
                    val phone = a?.phone.toString()
                    val email = a?.email.toString()
                    val img = a?.profile_pic.toString()
                    binding.idNamaUser.setText(name).toString()
                    binding.idEmail.setText(email).toString()
                    binding.idTlpnUser.setText(phone).toString()

                }
                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }

            })

    }

}