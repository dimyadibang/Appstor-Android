package com.mahadalynj.appstor.data.profile.helper

import android.content.Context
import android.content.SharedPreferences


class PreferencesHelper(context: Context) {

    private val PREF_TOKEN = "sharedprefkotlinbangdim16"
    private var sharedPreferences: SharedPreferences
    val editor: SharedPreferences.Editor


    init {
        sharedPreferences = context.getSharedPreferences(PREF_TOKEN, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun put(key: String, value: String){
        editor.putString(key, value)
            .apply()
    }

    fun getString(key: String) : String?{
        return sharedPreferences.getString(key, null)
    }

    fun put(key: String, value: Boolean){
        editor.putBoolean(key, value)
            .apply()
    }

    fun getBoolean(key: String) : Boolean{
        return sharedPreferences.getBoolean(key, false)
    }

    fun clear(){
        editor.clear()
            .apply()
    }



}