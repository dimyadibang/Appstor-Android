package com.mahadalynj.appstor.data.post

import com.google.gson.annotations.SerializedName

data class PostSetoran(
    @field:SerializedName("kitab")
    var kitab: Any? = null,

    @field:SerializedName("mahasantri")
    var mahasantri: Any? = null,

    @field:SerializedName("nilai")
    var nilai: Any? = null.toString(),

    @field:SerializedName("ketengan")
    var ketengan: String = null.toString(),
)