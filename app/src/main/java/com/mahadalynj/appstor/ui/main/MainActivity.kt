package com.mahadalynj.appstor.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.api.ApiClient
import com.mahadalynj.appstor.data.model.MainModel
import com.mahadalynj.appstor.data.model.MhSantriModel
import com.mahadalynj.appstor.data.model.UstadzModel
import com.mahadalynj.appstor.ui.adapter.MainAdapter
import com.mahadalynj.appstor.ui.login.LoginActivity
import com.mahadalynj.appstor.ui.utility.lightStatusBar
import com.mahadalynj.appstor.ui.utility.setfullScreen
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),View.OnClickListener  {
    private val TAG: String = "MainActivity"

    private lateinit var apiClient: ApiClient


    private lateinit var mainAdapter: MainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiClient = ApiClient()

        lightStatusBar(window)
        //setfullScreen(window)

        setupRecyclerView()
        getDataFromApi()
        getCoutUst()
        getCoutMhs()


        move_activity_list_ust.setOnClickListener(this)
        move_activity_list_mhs.setOnClickListener(this)
        icon_profile.setOnClickListener(this)



    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.move_activity_list_ust -> {
                val movelistUst = Intent(this@MainActivity, ListUstActivity::class.java)
                startActivity(movelistUst)

            }
            R.id.move_activity_list_mhs -> {
                val movelistMhs = Intent(this@MainActivity, ListMhsActivity::class.java)
                startActivity(movelistMhs)
            }
            R.id.icon_profile ->{
                val movePofile = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity((movePofile))
            }
        }
    }

    private fun getCoutMhs() {
        showLoading(true)
        refreshApp()
        apiClient.getApiService(this).datamhsantri()
            .enqueue(object : Callback<MhSantriModel> {
                override fun onFailure(call: Call<MhSantriModel>, t: Throwable) {
                    printLog(t.toString())
                    showLoading(false)
                    refreshApp()
                }

                override fun onResponse(
                    call: Call<MhSantriModel>,
                    response: Response<MhSantriModel>
                ) {
                    refreshApp()
                    showLoading(false)
                    if (response.isSuccessful) {
                        count_mhsantri.text = response.body()?.count.toString()
                    }
                }
            })

    }

    private fun getCoutUst() {
        showLoading(true)
        refreshApp()
        apiClient.getApiService(this).dataustad()
            .enqueue(object : Callback<UstadzModel> {
                override fun onFailure(call: Call<UstadzModel>, t: Throwable) {
                    printLog(t.toString())
                    showLoading(false)
                    refreshApp()
                }

                override fun onResponse(
                    call: Call<UstadzModel>,
                    response: Response<UstadzModel>
                ) {
                    refreshApp()
                    showLoading(false)
                    if (response.isSuccessful) {
                        count_ust.text = response.body()?.count.toString()
                    }
                }
            })
    }


    private fun setupRecyclerView() {
        mainAdapter = MainAdapter(arrayListOf())
        rv_setoran.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mainAdapter
        }
    }

    private fun getDataFromApi() {
        showLoading(true)
        refreshApp()


        apiClient.getApiService(this).data()
            .enqueue(object : Callback<MainModel> {
                override fun onFailure(call: Call<MainModel>, t: Throwable) {
                    printLog(t.toString())
                    showLoading(false)
                    refreshApp()
                }

                override fun onResponse(
                    call: Call<MainModel>,
                    response: Response<MainModel>
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
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

    private fun showResult(a: MainModel) {
        for (result in a.results) printLog("nama: ${result.namaMahasantri.name}")
        mainAdapter.setData(a.results)
    }


    private fun refreshApp() {
        swipeToRefresh.setOnRefreshListener {
            Toast.makeText(this, "Sudah di refresh", Toast.LENGTH_SHORT).show()
            swipeToRefresh.isRefreshing = false
        }
    }
}
