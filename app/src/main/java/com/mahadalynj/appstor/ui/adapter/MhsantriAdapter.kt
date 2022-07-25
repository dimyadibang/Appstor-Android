package com.mahadalynj.appstor.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.data.model.MainModel
import com.mahadalynj.appstor.data.model.MhSantriModel
import kotlinx.android.synthetic.main.item_list_mhs.view.*
import kotlinx.android.synthetic.main.item_setoran.view.*

class MhsantriAdapter (var results: ArrayList<MhSantriModel.DataSantri>, val listener: OnAdapterListener):
    RecyclerView.Adapter<MhsantriAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from( parent.context ).inflate( R.layout.item_list_mhs,
            parent, false)
    )

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val results = results[position]
        holder.view.tv_nama_mhsantri.text = results.name
        holder.view.tv_jk_mhs.text = results.jk
        holder.view.tv_status_mhs.text = results.status

        holder.view.setOnClickListener { listener.onClick( results) }

    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(data: ArrayList<MhSantriModel.DataSantri>){
        this.results.clear()
        this.results.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(results: MhSantriModel.DataSantri)
    }
}