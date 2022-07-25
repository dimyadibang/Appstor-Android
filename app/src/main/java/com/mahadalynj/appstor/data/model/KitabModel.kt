package com.mahadalynj.appstor.data.model



data class KitabModel(
    val results: ArrayList<Result>
){
    data class Result(
        val id: Int,
        val awalan: String,
        val halaman: String,
        val fasol: String,
        val bab: String,
        val isi: String,
    )
}