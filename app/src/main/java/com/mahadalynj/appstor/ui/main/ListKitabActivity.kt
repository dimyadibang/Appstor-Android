package com.mahadalynj.appstor.ui.main

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
import com.mahadalynj.appstor.data.model.KitabModel
import com.mahadalynj.appstor.data.profile.helper.PreferencesHelper
import com.mahadalynj.appstor.ui.adapter.KitabAdapter
import com.mahadalynj.appstor.ui.detail.DetailKitabActivity
import com.mahadalynj.appstor.ui.utility.lightStatusBar
import com.mahadalynj.appstor.ui.utility.setfullScreen
import kotlinx.android.synthetic.main.activity_list_kitab.*
import kotlinx.android.synthetic.main.activity_main.swipeToRefresh
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListKitabActivity : AppCompatActivity() {


    private lateinit var apiClient: ApiClient

    private lateinit var kitabAdapter: KitabAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_kitab)
        apiClient = ApiClient()

        lightStatusBar(window)
        setfullScreen(window)

        setupRecyclerView()
        getDataFromApi()

    }




    /*private fun createPost() {
        val idMhs = intent.getStringExtra("id_mhs")
        val reg = PostSetoran()
        reg.mahasantri = idMhs
        reg.kitab = result.id
        ApiClient.endpoint.postSetoran(
            reg
        ).enqueue(object : Callback<SetoranModel> {
            override fun onResponse(
                call: Call<SetoranModel>,
                response: Response<SetoranModel>
            ) {
                val toast = Toast.makeText(applicationContext, "Setoran $idMhs " +
                        "Pada halaman ${result.halaman}" +
                        "Kalimamat ${result.awalan}" +
                        "telah Sukses", Toast.LENGTH_SHORT)
                toast.show()
            }

            override fun onFailure(call: Call<SetoranModel>, t: Throwable) {
                val toast = Toast.makeText(applicationContext, "Gagal", Toast.LENGTH_SHORT)
                toast.show()
            }

        })
    }
*/

    private fun setupRecyclerView(){
        val idMhs = intent.getStringExtra("id_mhs")
        val idMhsName =  intent.getStringExtra("id_nama_mhs" )
        kitabAdapter = KitabAdapter(arrayListOf(), object : KitabAdapter.OnAdapterListener {
            override fun onClick(results: KitabModel.Result) {
                startActivity(
                    Intent(this@ListKitabActivity, DetailKitabActivity::class.java)
                        .putExtra("intent_id_kt", results.id.toString())
                        .putExtra("intent_awalan", results.awalan)
                        .putExtra("intent_halaman", results.halaman)
                        .putExtra("intent_fasol", results.fasol)
                        .putExtra("intent_bab", results.bab)
                        .putExtra("intent_isi", results.isi)
                        .putExtra("intent_id_mhsantri", idMhs)
                        .putExtra("intent_id_mhsantri_name", idMhsName)
                )

            }
        })
        rv_kitab.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = kitabAdapter
        }
    }

    private fun getDataFromApi() {
        showLoading(true)
        refreshApp()
        apiClient.getApiService(this).datakitab()
            .enqueue(object : Callback<KitabModel> {
                override fun onFailure(call: Call<KitabModel>, t: Throwable) {
                    printLog(t.toString())
                    showLoading(false)
                    refreshApp()
                }

                override fun onResponse(
                    call: Call<KitabModel>,
                    response: Response<KitabModel>
                ) {
                    refreshApp()
                    showLoading(false)
                    if (response.isSuccessful) {
                        showResult(response.body()!!)
                    }
                }
            })
    }

    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    private fun showLoading(loading: Boolean) {
        when (loading) {
            true -> progressBar3.visibility = View.VISIBLE
            false -> progressBar3.visibility = View.GONE
        }
    }

    private fun showResult(a: KitabModel) {
        for (result in a.results) printLog("nama: ${result.bab}")
        kitabAdapter.setData(a.results)



    }


    private fun refreshApp() {
        swipeToRefresh.setOnRefreshListener {
            Toast.makeText(this, "Sudah di refresh", Toast.LENGTH_SHORT).show()
            swipeToRefresh.isRefreshing = false
        }
    }
}
