package com.mahadalynj.appstor.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.api.ApiClient
import com.mahadalynj.appstor.data.model.*
import com.mahadalynj.appstor.data.profile.UserModel
import com.mahadalynj.appstor.data.profile.helper.Constant
import com.mahadalynj.appstor.data.profile.helper.PreferencesHelper
import com.mahadalynj.appstor.ui.adapter.MainAdapter
import com.mahadalynj.appstor.ui.detail.DetailMhsActivity
import com.mahadalynj.appstor.ui.utility.lightStatusBar
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),View.OnClickListener  {
    private val TAG: String = "MainActivity"

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: PreferencesHelper
    private lateinit var mainAdapter: MainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiClient = ApiClient()
        PreferencesHelper(this)
        lightStatusBar(window)
        //setfullScreen(window)
        sessionManager = PreferencesHelper(this)
        getUstadUser()
        setupRecyclerView()
        getDataFromApi()
        getCoutUst()
        getCoutMhs()

        move_activity_list_ust.setOnClickListener(this)
        move_activity_list_mhs.setOnClickListener(this)
        btn_profile.setOnClickListener(this)

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
            R.id.btn_profile ->{
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
                        count_ust.text = response.body()?.count.toString().trim()
                    }
                }
            })
    }
    private fun getUstadUser(){
        showLoading(true)
        val user = sessionManager.getString(Constant.PREF_IS_ID)
        val parameters= HashMap<String, String>()
        parameters["user"] = "$user"
        apiClient.getApiService(this).dataustadzuser(parameters)
            .enqueue(object : Callback<UserModel>{
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    val data = response.body()?.datauser?.let { ArrayList(it) }
                    val a = data?.get(0)
                    Log.e("email", data?.size.toString())
                    Log.e("a", a?.id.toString())
                    nm_user.text = a?.name.toString()
                    Log.e("a", a?.phone.toString())
                    Log.e("a", a?.email.toString())
                    val img = a?.profile_pic.toString()

                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }

            })

    }

    private fun setupRecyclerView() {
        mainAdapter = MainAdapter(arrayListOf(),object :MainAdapter.OnAdapterListener{
            override fun onClick(results: MainModel.Result) {
                startActivity(
                    Intent(this@MainActivity, DetailMhsActivity::class.java)
                        .putExtra("intent_ids", results.mahasantri)
                )

            }
        }
        )
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
            true -> progressBarMain.visibility = View.VISIBLE
            false -> progressBarMain.visibility = View.GONE
        }
    }

    private fun showResult(a: MainModel) {
        for (result in a.results) printLog("nama: ${result.namaMahasantri.name}")
        mainAdapter.setData(a.results)
    }


    private fun refreshApp() {
        swipeToRefresh.setOnRefreshListener {
            getUstadUser()
            setupRecyclerView()
            getDataFromApi()
            getCoutUst()
            getCoutMhs()
            swipeToRefresh.isRefreshing = false
        }
    }
}
