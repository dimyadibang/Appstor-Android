package com.mahadalynj.appstor.data.model

import com.google.gson.annotations.SerializedName

data class MhSantriModel (
    val count: Int,

    @SerializedName("results")
    val datasantri: ArrayList<DataSantri>
){
    data class DataSantri(
        val id: Int,
        val name: String,
        val jk: String,
        val status: String,
    )
}
