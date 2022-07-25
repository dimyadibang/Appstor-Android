package com.mahadalynj.appstor.data.model

import com.google.gson.annotations.SerializedName

data class SetoranModel (
    val count: Int,
    val results: ArrayList<Result>
){
    data class Result(
        val id: Int,
        val mahasantri: String,
        val kitab: String,
        val nilai: String,
        val ketengan:String ,

        @field:SerializedName("date_created")
        val tanggal: String,

        @field:SerializedName("hal_kitab")
        val halKitab: halKitab,
    )

    data class halKitab(
        val awalan: String,
        val halaman: String

    )
}
