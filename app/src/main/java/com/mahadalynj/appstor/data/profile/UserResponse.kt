package com.mahadalynj.appstor.data.profile

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)
