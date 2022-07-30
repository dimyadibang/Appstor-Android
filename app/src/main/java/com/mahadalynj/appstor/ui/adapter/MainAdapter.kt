package com.mahadalynj.appstor.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.data.model.KitabModel
import com.mahadalynj.appstor.data.model.MainModel
import kotlinx.android.synthetic.main.item_setoran.view.*

class MainAdapter(var results: ArrayList<MainModel.Result>,val listener: MainAdapter.OnAdapterListener):
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from( parent.context ).inflate( R.layout.item_setoran,
            parent, false)
    )

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.view.tv_nama_setoran.text = result.namaMahasantri.name
        holder.view.tv_hal_setoran.text = result.halKitab.halaman
        holder.view.tv_tgl_setoran.text = result.tanggal
        holder.view.btn_detail_mhs.setOnClickListener { listener.onClick( result ) }
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(data: ArrayList<MainModel.Result>){
        this.results.clear()
        this.results.addAll(data)
        notifyDataSetChanged()
    }
    interface OnAdapterListener {
        fun onClick(results: MainModel.Result)
    }


}