package com.mahadalynj.appstor.ui.detail

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.api.ApiClient
import com.mahadalynj.appstor.data.model.DetailMhsantriModel
import com.mahadalynj.appstor.data.model.SetoranModel
import com.mahadalynj.appstor.ui.adapter.SetoranMhsAdapter
import com.mahadalynj.appstor.ui.main.ListKitabActivity
import com.mahadalynj.appstor.ui.utility.lightStatusBar
import kotlinx.android.synthetic.main.activity_detail_mhs.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMhsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var apiClient: ApiClient

    private lateinit var setoranAdapter: SetoranMhsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_mhs)
        apiClient = ApiClient()

        lightStatusBar(window)
        //setfullScreen(window)
        getDataFromApi()
        setupRecyclerView()
        getMhsDetail()



        move_activity_list_kitab.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val name = intent.getStringExtra("intent_name")
        val idMhs = intent.getStringExtra("intent_ids")
        when (v?.id) {
            R.id.move_activity_list_kitab -> {
                startActivity(
                    Intent(this@DetailMhsActivity, ListKitabActivity::class.java)
                        .putExtra("id_mhs", idMhs)
                        .putExtra("id_nama_mhs", name)
                )

            }
        }
    }

    private fun setupRecyclerView() {
        setoranAdapter =
            SetoranMhsAdapter(arrayListOf(), object : SetoranMhsAdapter.OnAdapterListener {
                override fun onClick(results: SetoranModel.Result) {
                    val a = AlertDialog.Builder(this@DetailMhsActivity)
                    a.setTitle("Setoran Ini")
                    a.setMessage("Apakah Ingin dihapus?")
                    a.setPositiveButton("ya") { _: DialogInterface?, _: Int ->
                        val id = results.id
                        showLoading(false)
                        apiClient.getApiService(applicationContext).deletesetoranmhs(id)
                            .enqueue(object : Callback<Int> {
                                override fun onFailure(call: Call<Int>, t: Throwable) {
                                    printLog(t.toString())
                                    showLoading(false)
                                    refreshApp()
                                }

                                override fun onResponse(
                                    call: Call<Int>,response: Response<Int>) {
                                    refreshApp()
                                    showLoading(false)
                                    getDataFromApi()
                                    setupRecyclerView()
                                    getMhsDetail()
                                    Toast.makeText(applicationContext, "Setoran Sudah ter-hapus", Toast.LENGTH_SHORT).show()
                                    Log.i("Sukses",response.toString())
                                }
                            })
                    }
                    a.setNegativeButton("Tidak") { _: DialogInterface?, _: Int ->
                        Toast.makeText(applicationContext, "BATAL", Toast.LENGTH_SHORT).show()
                    }
                    a.show()




                }
            }
            )
        rv_setoran_mhs.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = setoranAdapter
        }
    }

    private fun getMhsDetail() {
        val id = intent.getStringExtra("intent_ids")
        Log.i("val id", id.toString())
        showLoading(true)
        refreshApp()
        apiClient.getApiService(this).datadetailmhsantri(id.toString().toInt())
            .enqueue(object : Callback<DetailMhsantriModel> {
                override fun onFailure(call: Call<DetailMhsantriModel>, t: Throwable) {
                    printLog(t.toString())
                    showLoading(false)
                    refreshApp()
                }

                override fun onResponse(
                    call: Call<DetailMhsantriModel>,
                    response: Response<DetailMhsantriModel>
                ) {
                    refreshApp()
                    showLoading(false)
                    Log.i("Sukses", response.toString())
                    val data = response.body()
                    nama_mhs_detail.text = data?.name.toString()
                    jk_mhs_detail.text = data?.jk.toString()
                    status_mhs_detail.text = data?.status.toString()
                }
            })
    }

    private fun getDataFromApi() {
        val id = intent.getStringExtra("intent_ids")
        showLoading(true)
        refreshApp()
        val parameters = HashMap<String, String>()
        parameters["mahasantri"] = "$id"
        apiClient.getApiService(this).datasetoranmhs(parameters)
            .enqueue(object : Callback<SetoranModel> {
                override fun onFailure(call: Call<SetoranModel>, t: Throwable) {
                    printLog(t.toString())
                    showLoading(false)
                    refreshApp()
                }

                override fun onResponse(
                    call: Call<SetoranModel>, response: Response<SetoranModel>
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
            getDataFromApi()
            setupRecyclerView()
            getMhsDetail()
            swipeToRefresh.isRefreshing = false

        }
    }

}