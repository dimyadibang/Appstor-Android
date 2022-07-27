package com.mahadalynj.appstor.ui.main


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.api.ApiClient
import com.mahadalynj.appstor.data.profile.UserModel
import com.mahadalynj.appstor.data.profile.UserUpdate
import com.mahadalynj.appstor.data.profile.helper.Constant
import com.mahadalynj.appstor.data.profile.helper.PreferencesHelper
import com.mahadalynj.appstor.ui.login.LoginActivity
import com.mahadalynj.appstor.ui.utility.lightStatusBar
import com.mahadalynj.appstor.ui.utility.setfullScreen
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileActivity : AppCompatActivity() {

    private lateinit var apiClient: ApiClient
    lateinit var sharedPreferences : PreferencesHelper

    lateinit var imageView: ImageView
    lateinit var button: Button
    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        apiClient = ApiClient()
        lightStatusBar(window)
        setfullScreen(window)

        sharedPreferences = PreferencesHelper(this)



        getUstadUser()

        btn_logout.setOnClickListener {
            sharedPreferences.clear()
          Intent(this, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                Toast.makeText(applicationContext, "Berhasil LogOut", Toast.LENGTH_SHORT).show()

            }
        }
        initAction()

        imageView = findViewById(R.id.profile_image)
        button = findViewById(R.id.pickImage)
        button.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }


    private fun initAction(){
        save_update_user.setOnClickListener {
            editUser()
        }

    }

    private fun editUser() {
        val idUstadUstad = sharedPreferences.getString(Constant.ID_USTAD_USER)
       // val image = imageView
        val update = UserUpdate()

        update.name = id_nama_user.text.toString().trim()
        update.email = id_email.text.trim().toString()
        update.phone = id_tlpn_user.text.trim().toString()



        apiClient.getApiService(this).editustaduser(idUstadUstad.toString().toInt(),update
        //    image
        ).enqueue((object : Callback<UserModel> {
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                     if ( response.isSuccessful) {
                        Log.i("?", response.toString())
                        Toast.makeText(applicationContext, "Sudah ter-update", Toast.LENGTH_SHORT).show()

                    }else {
                         Toast.makeText(
                             applicationContext,
                             "gagal upload",
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

            }))

    }

    private fun getUstadUser(){

        val user = sharedPreferences.getString(Constant.PREF_IS_ID)
        val parameters= HashMap<String, String>()
        parameters["user"] = "$user"
        apiClient.getApiService(this).datausta(parameters)
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
                    Picasso.get()
                        .load(img)
                        .fit()
                        .centerCrop()
                        .into(profile_image)
                    id_nama_user.setText(name).toString()
                    id_email.setText(email).toString()
                    id_tlpn_user.setText(phone).toString()



                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }

            })

    }
}