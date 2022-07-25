package com.mahadalynj.appstor.api

import com.mahadalynj.appstor.data.model.*
import com.mahadalynj.appstor.data.post.PostSetoran
import com.mahadalynj.appstor.data.profile.UserRequest
import com.mahadalynj.appstor.data.profile.UserResponse
import com.mahadalynj.appstor.data.profile.helper.Constant.Companion.PREF_IS_TOKEN
import retrofit2.Call
import retrofit2.http.*
import com.mahadalynj.appstor.data.profile.helper.Constant
import com.mahadalynj.appstor.data.profile.helper.PreferencesHelper
import okhttp3.Response
import okhttp3.Interceptor


interface ApiService {


    @POST("api-token-auth/")
   // @POST("api-auth/login/")
    fun login(
        @Body userRequest: UserRequest
    ): Call<UserResponse>


    @GET("api/setoran/")
    fun data(): Call<MainModel>

    @GET("api/ustadz/")
    fun dataustad(): Call<UstadzModel>


    @GET("api/mahasantri/")
    fun datamhsantri(): Call<MhSantriModel>

    @GET("api/setoran/")
    fun datasetoranmhs(
        @QueryMap parameters: HashMap<String, String>
    ): Call<SetoranModel>

    @GET("api/kitab/")
    fun datakitab(): Call<KitabModel>


    @POST("api/setoran/")
    fun postSetoran(@Body req: PostSetoran): Call<SetoranModel>



}
