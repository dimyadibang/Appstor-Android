package com.mahadalynj.appstor.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.api.ApiClient
import com.mahadalynj.appstor.data.model.MhSantriModel
import com.mahadalynj.appstor.data.profile.helper.PreferencesHelper
import com.mahadalynj.appstor.ui.adapter.MhsantriAdapter
import com.mahadalynj.appstor.ui.detail.DetailMhsActivity
import com.mahadalynj.appstor.ui.utility.lightStatusBar
import com.mahadalynj.appstor.ui.utility.setfullScreen
import kotlinx.android.synthetic.main.activity_list_mhs.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListMhsActivity : AppCompatActivity() {

    private lateinit var apiClient: ApiClient
    private val TAG: String = "ListMhsActivity"

    private lateinit var mhsAdapter: MhsantriAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_mhs)
        apiClient = ApiClient()

        lightStatusBar(window)
        setfullScreen(window)

        setupRecyclerView()
        getDataFromApi()

    }

    private fun setupRecyclerView() {
        mhsAdapter = MhsantriAdapter(arrayListOf(),
            object : MhsantriAdapter.OnAdapterListener {
                override fun onClick(results: MhSantriModel.DataSantri) {
                    startActivity(
                        Intent(this@ListMhsActivity, DetailMhsActivity::class.java)
                            .putExtra("intent_name", results.name)
                            .putExtra("intent_jk", results.jk)
                            .putExtra("intent_status", results.status)
                            .putExtra("intent_id", results.id.toString())
                            .putExtra("intent_ids", results.id.toString())

                    )
                }
            })
        rv_mahasantri.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mhsAdapter
        }
    }
    private fun getDataFromApi() {
        showLoading(true)
        apiClient.getApiService(this).datamhsantri()
            .enqueue(object : Callback<MhSantriModel> {
                override fun onFailure(call: Call<MhSantriModel>, t: Throwable) {
                    printLog(t.toString())
                    showLoading(false)
                }

                override fun onResponse(
                    call: Call<MhSantriModel>,
                    response: Response<MhSantriModel>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        showResult2(response.body()!!)
                        count_list_mhs.text = response.body()?.count.toString()
                    }
                }
            })
    }
    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    private fun showLoading(loading: Boolean) {
        when (loading) {
            true -> progressBar1.visibility = View.VISIBLE
            false -> progressBar1.visibility = View.GONE
        }
    }

    private fun showResult2(a: MhSantriModel) {
        for (result1 in a.datasantri) printLog("nama: ${result1.name}")
        mhsAdapter.setData(a.datasantri)
    }
}