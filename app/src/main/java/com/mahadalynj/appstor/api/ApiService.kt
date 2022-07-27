package com.mahadalynj.appstor.api

import com.mahadalynj.appstor.data.model.*
import com.mahadalynj.appstor.data.post.PostSetoran
import com.mahadalynj.appstor.data.profile.UserModel
import com.mahadalynj.appstor.data.profile.UserRequest
import com.mahadalynj.appstor.data.profile.UserResponse
import com.mahadalynj.appstor.data.profile.UserUpdate
import retrofit2.Call
import retrofit2.http.*


interface ApiService {


    @POST("api-token-auth/")
   // @POST("api-auth/login/")
    fun login(
        @Body userRequest: UserRequest
    ): Call<UserResponse>

    @GET("api/ustadz/")
    fun datausta(
        @QueryMap parameters: HashMap<String, String>
    ): Call<UserModel>
        //////
    //@Multipart
    @PUT("api/ustadz/{id}/")
    fun editustaduser(
            @Path("id") id: Int, @Body update: UserUpdate
            //@Part part: ImageView
        ): Call<UserModel>




    @GET("api/ustadz/")
    fun dataustad(): Call<UstadzModel>

    @GET("api/mahasantri/")
    fun datamhsantri(): Call<MhSantriModel>

    @GET("api/setoran/")
    fun data(): Call<MainModel>

    @GET("api/setoran/")
    fun datasetoranmhs(
        @QueryMap parameters: HashMap<String, String>
    ): Call<SetoranModel>

    @GET("api/kitab/")
    fun datakitab(): Call<KitabModel>


    @POST("api/setoran/")
    fun postSetoran(@Body req: PostSetoran): Call<SetoranModel>

  /*  @Multipart
    @PUT("api/ustadz/{id}/")
    fun editustaduser(@Path("id") id: Int,
        //@Part part: Part?,
        //@Part("profile_pic") part4: Part?,
                      @Body update: UserUpdate?, @Part("profile_pic") part4: ImageView
    ) : Call<UserModel>*/




}
