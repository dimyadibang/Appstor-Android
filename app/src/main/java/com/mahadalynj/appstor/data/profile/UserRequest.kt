package com.mahadalynj.appstor.data.profile

import com.google.gson.annotations.SerializedName

data class UserRequest(

    @field:SerializedName("username")
    var username: Any? = null,

    @field:SerializedName("password")
    var password: Any? = null,
)
