package com.mahadalynj.appstor.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.api.ApiClient
import com.mahadalynj.appstor.data.model.UstadzModel
import com.mahadalynj.appstor.data.profile.helper.PreferencesHelper
import com.mahadalynj.appstor.ui.adapter.UstadzAdapter
import com.mahadalynj.appstor.ui.detail.DetailUstActivity
import com.mahadalynj.appstor.ui.utility.lightStatusBar
import com.mahadalynj.appstor.ui.utility.setfullScreen
import kotlinx.android.synthetic.main.activity_list_ust.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListUstActivity : AppCompatActivity() {

    private lateinit var apiClient: ApiClient
    private val TAG: String = "ListUstActivity"

    private lateinit var ustAdapter: UstadzAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_ust)

        lightStatusBar(window)
        setfullScreen(window)

        apiClient = ApiClient()
        setupRecyclerView()
        getDataFromApi()

    }

    private fun setupRecyclerView(){
        ustAdapter = UstadzAdapter(arrayListOf(),
            object : UstadzAdapter.OnAdapterListener {
            override fun onClick(result: UstadzModel.DataUst) {
                startActivity(
                    Intent(this@ListUstActivity, DetailUstActivity::class.java)
                        .putExtra("intent_name", result.name)
                        .putExtra("intent_email", result.email)
                        .putExtra("intent_phone", result.phone)
                        .putExtra("intent_image", result.profile_pic)
                )
            }
        })

        rv_ust.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ustAdapter
        }
    }

    private fun getDataFromApi(){
        showLoading(true)
        apiClient.getApiService(this).dataustad()
            .enqueue(object : Callback<UstadzModel> {
                override fun onFailure(call: Call<UstadzModel>, t: Throwable) {
                    printLog( t.toString() )
                    showLoading(false)
                }
                override fun onResponse(
                    call: Call<UstadzModel>,
                    response: Response<UstadzModel>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        count_list_ust.text = response.body()?.count.toString()
                        showResult( response.body()!! )
                    }
                }
            })
    }

    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    private fun showLoading(loading: Boolean) {
        when(loading) {
            true -> progressBar2.visibility = View.VISIBLE
            false -> progressBar2.visibility = View.GONE
        }
    }

    private fun showResult(results: UstadzModel) {
        for (result in results.dataust) printLog( "nama: ${result.name}" )
        ustAdapter.setData( results.dataust )
    }
}
