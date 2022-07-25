package com.mahadalynj.appstor.ui.detail

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.api.ApiClient
import com.mahadalynj.appstor.data.model.SetoranModel
import com.mahadalynj.appstor.data.profile.helper.PreferencesHelper
import com.mahadalynj.appstor.ui.adapter.SetoranMhsAdapter
import com.mahadalynj.appstor.ui.main.ListKitabActivity
import kotlinx.android.synthetic.main.activity_detail_mhs.*
import kotlinx.android.synthetic.main.activity_detail_mhs.progressBar
import kotlinx.android.synthetic.main.activity_detail_mhs.swipeToRefresh
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMhsActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var apiClient: ApiClient

    private lateinit var setoranAdapter: SetoranMhsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_mhs)
        apiClient = ApiClient()
        val name = intent.getStringExtra("intent_name")
        val jk = intent.getStringExtra("intent_jk")
        val status = intent.getStringExtra("intent_status")
        nama_mhs_detail.text = name
        jk_mhs_detail.text = jk
        status_mhs_detail.text = status
        getDataFromApi()
        setupRecyclerView()



        move_activity_list_kitab.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val name = intent.getStringExtra("intent_name")
        val idMhs = intent.getStringExtra("intent_ids")
        when (v?.id) {
            R.id.move_activity_list_kitab -> {
                startActivity(
                    Intent(this@DetailMhsActivity, ListKitabActivity::class.java)
                        .putExtra("id_mhs", idMhs )
                        .putExtra("id_nama_mhs", name )
                )

            }
        }
    }

    private fun setupRecyclerView() {
        setoranAdapter = SetoranMhsAdapter(arrayListOf())
        rv_setoran_mhs.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = setoranAdapter
        }
    }
    private fun getDataFromApi() {
        val id = intent.getStringExtra("intent_id")
        showLoading(true)
        refreshApp()
        val parameters= HashMap<String, String>()
        parameters["mahasantri"] = "$id"
        apiClient.getApiService(this).datasetoranmhs(parameters)
            .enqueue(object : Callback<SetoranModel> {
                override fun onFailure(call: Call<SetoranModel>, t: Throwable) {
                    printLog(t.toString())
                    showLoading(false)
                    refreshApp()
                }

                override fun onResponse(
                    call: Call<SetoranModel>,
                    response: Response<SetoranModel>
                ) {
                    refreshApp()
                    showLoading(false)
                    if (response.isSuccessful) {
                        showResult(response.body()!!)
                        val a = response.body()?.count
                        val b = 3
                        val c = 4
                        val d = (a?.div(b))?.plus(c)
                        count_setoran_mhsantri.text = ("$d")
                    }
                }
            })
    }

    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    private fun showLoading(loading: Boolean) {
        when (loading) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

    private fun showResult(a: SetoranModel) {
        for (result in a.results) printLog("nama: ${result.mahasantri}")
        setoranAdapter.setData(a.results)
    }


    private fun refreshApp() {
        swipeToRefresh.setOnRefreshListener {
            Toast.makeText(this, "Sudah di refresh", Toast.LENGTH_SHORT).show()
            swipeToRefresh.isRefreshing = false
        }
    }

}