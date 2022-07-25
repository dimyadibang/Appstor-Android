package com.mahadalynj.appstor.data.model

import com.google.gson.annotations.SerializedName

data class UstadzModel(
    val count: Int,

    @SerializedName("results")
    val dataust: ArrayList<DataUst>
){
    data class DataUst(
        val id: Int,
        val name: String,
        val phone: String,
        val email: String,
        val profile_pic: String,
    )
}
