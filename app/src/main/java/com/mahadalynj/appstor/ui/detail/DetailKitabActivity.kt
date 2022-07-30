package com.mahadalynj.appstor.ui.detail

import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.api.ApiClient
import com.mahadalynj.appstor.data.model.SetoranModel
import com.mahadalynj.appstor.data.post.PostSetoran
import com.mahadalynj.appstor.ui.main.ListKitabActivity
import com.mahadalynj.appstor.ui.utility.lightStatusBar
import kotlinx.android.synthetic.main.activity_detail_kitab.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailKitabActivity : AppCompatActivity(),View.OnClickListener  {

    private val tag: String = "DetailKitabActivity"
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kitab)
        apiClient = ApiClient()

        lightStatusBar(window)
        //setfullScreen(window)

        val idMhs = intent.getStringExtra("intent_id_mhsantri")
        val idMhsName = intent.getStringExtra("intent_id_mhsantri_name")
        val idAwalan = intent.getStringExtra("intent_awalan")
        val idHalaman = intent.getStringExtra("intent_halaman")
        val idFashol = intent.getStringExtra("intent_fasol")
        val idBab = intent.getStringExtra("intent_bab")
        val idIsi = intent.getStringExtra("intent_isi")

        id_halaman_kitab_detail.text = idHalaman
        tv_fashol_kitab_detail.text = idFashol
        tv_bab_kitab_detail.text = idBab
        tv_isi_kitab_detail.text = idIsi

        val nilai = listOf("A","B","C")
        val adapter = ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,nilai)
        sp_Nilai.adapter = adapter


        id_button.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_button -> {
                createPost()

            }
        }
    }

    private fun createPost() {
        val idKtb = intent.getStringExtra("intent_id_kt")
        val idMhs = intent.getStringExtra("intent_id_mhsantri")
        val nilai= sp_Nilai.selectedItem.toString()
        val ket = id_keterangan.text.toString()

        val reg = PostSetoran()
        reg.mahasantri = idMhs
        reg.kitab = idKtb
        reg.nilai = nilai
        reg.ketengan = ket

        val idHalaman = intent.getStringExtra("intent_halaman")
        val idAwalan = intent.getStringExtra("intent_awalan")

        apiClient.getApiService(this).postSetoran(reg)
            .enqueue(object : Callback<SetoranModel> {
            override fun onResponse(
                call: Call<SetoranModel>,
                response: Response<SetoranModel>
            ) {
                if (response.isSuccessful){
                val toast = Toast.makeText(applicationContext, "Setoran Pada halaman $idHalaman " +
                        "Kalimamat $idAwalan " +
                        "telah Sukses", Toast.LENGTH_SHORT)
                toast.show()
                    onBackPressed()
                    return

                }
            }
            override fun onFailure(call: Call<SetoranModel>, t: Throwable) {
                val toast = Toast.makeText(applicationContext, "Gagal", Toast.LENGTH_SHORT)
                toast.show()
            }

        })
    }

}