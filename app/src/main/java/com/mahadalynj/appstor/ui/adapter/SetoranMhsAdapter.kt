package com.mahadalynj.appstor.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.data.model.MainModel
import com.mahadalynj.appstor.data.model.SetoranModel
import kotlinx.android.synthetic.main.item_setoran.view.*
import kotlinx.android.synthetic.main.item_setoran_mhs.view.*

class SetoranMhsAdapter (var results: ArrayList<SetoranModel.Result>,val listener: SetoranMhsAdapter.OnAdapterListener):
    RecyclerView.Adapter<SetoranMhsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from( parent.context ).inflate( R.layout.item_setoran_mhs,
            parent, false)
    )

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.view.awalan_setoran_mhs.text = result.halKitab.awalan
        holder.view.tv_hal_setoran_mhs.text = result.halKitab.halaman
        holder.view.tv_tgl_setoran_mhs.text = result.tanggal
        holder.view.tv_nilai_setoran_mhs.text= result.nilai
        holder.view.tv_ket_setoran_mhs.text = result.ketengan
        holder.view.btn_delete_setoran.setOnClickListener { listener.onClick( result ) }

    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(data: ArrayList<SetoranModel.Result>){
        this.results.clear()
        this.results.addAll(data)
        notifyDataSetChanged()
    }
    interface OnAdapterListener {
        fun onClick(results: SetoranModel.Result)
    }


}