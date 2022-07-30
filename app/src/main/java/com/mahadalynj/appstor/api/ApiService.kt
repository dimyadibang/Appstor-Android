package com.mahadalynj.appstor.api

import com.mahadalynj.appstor.data.model.*
import com.mahadalynj.appstor.data.post.PostSetoran
import com.mahadalynj.appstor.data.profile.UserModel
import com.mahadalynj.appstor.data.profile.UserRequest
import com.mahadalynj.appstor.data.profile.UserResponse
import com.mahadalynj.appstor.data.profile.UserUpdate
import okhttp3.Authenticator
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {


    @POST("api-token-auth/")
   // @POST("api-auth/login/")
    fun login(
        @Body userRequest: UserRequest
    ): Call<UserResponse>

    @GET("api/ustadz/")
    fun dataustadzuser(
        @QueryMap parameters: HashMap<String, String>
    ): Call<UserModel>
        //////
    @PUT("api/ustadz/{id}/")
    fun editustaduser(
            @Path("id") id: Int, @Body update: UserUpdate
            //@Part part: ImageView
        ): Call<UserModel>

   /* @Multipart
    @PUT("api/ustadz/{id}/")
    fun uploadImage(
        @Path("id") id: Int,
        //@Part("profile_pic") photo: RequestBody,
        @Part ("profile_pic") value : MultipartBody.Part,

       // @Part ("desc") desc : RequestBody
    ) : Call<UserModel>*/


    @GET("api/ustadz/")
    fun dataustad(): Call<UstadzModel>


    @GET("api/mahasantri/")
    fun datamhsantri(): Call<MhSantriModel>

    @GET("api/mahasantri/{id}/")
    fun datadetailmhsantri(
        @Path("id") id: Int): Call<DetailMhsantriModel>

    @GET("api/setoran/")
    fun data(): Call<MainModel>

    @GET("api/setoran/")
    fun datasetoranmhs(
        @QueryMap parameters: HashMap<String, String>
    ): Call<SetoranModel>

    @DELETE("api/setoran/{id}/")
    fun deletesetoranmhs(
        @Path("id") id: Int,
    ): Call<Int>


    @GET("api/kitab/")
    fun datakitab(): Call<KitabModel>


    @POST("api/setoran/")
    fun postSetoran(@Body req: PostSetoran): Call<SetoranModel>


    /*@PATCH("/api/users/{username}/")
    fun changeUserPhoto(
        @Header("Authorization") token: String?,
        @Path("username") userName: String?,
        @Body photo: RequestBody?
    ): Call<User?>?*/


   /* @PATCH("api/ustadz/{id}/")
    fun uploadImage(
        @Path("id") id: Int,
        @Body photo: RequestBody?
    ) : Call<UserModel>
*/



}
