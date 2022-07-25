package com.mahadalynj.appstor.api

import android.content.Context
import com.mahadalynj.appstor.ui.login.LoginActivity
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private lateinit var apiService: ApiService

    fun getApiService(context: Context) : ApiService {
        // Inisialisasi Retrofit jika belum di inisialisasi
        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://appstor.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context))
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }

        return apiService
    }

    /**
     * Inisilisasi OkhttpClient dengan interceptor
     */
    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor(context))
            .build()

    }


}