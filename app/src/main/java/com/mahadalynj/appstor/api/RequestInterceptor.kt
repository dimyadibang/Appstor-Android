package com.mahadalynj.appstor.api

import android.content.Context
import android.content.SharedPreferences
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.data.profile.helper.Constant
import com.mahadalynj.appstor.data.profile.helper.Constant.Companion.PREF_IS_TOKEN
import com.mahadalynj.appstor.data.profile.helper.PreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor (context: Context) : Interceptor {

    private val sessionManager = PreferencesHelper(context)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // Jika token ada di session manager, token sisipkan di request header
        sessionManager.getString(PREF_IS_TOKEN).let {
            requestBuilder
                .addHeader(
                "Authorization",
                "token $it"
            )
        }

        return chain.proceed(requestBuilder.build())
    }
}