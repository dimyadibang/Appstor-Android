package com.mahadalynj.appstor.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.data.model.KitabModel
import kotlinx.android.synthetic.main.item_list_kitab.view.*

class KitabAdapter(var results: ArrayList<KitabModel.Result>,val listener: OnAdapterListener):
    RecyclerView.Adapter<KitabAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from( parent.context ).inflate( R.layout.item_list_kitab,
            parent, false)
    )

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.view.tv_halaman_kitab.text = result.halaman
        holder.view.tv_awalan_kitab.text = result.awalan
        holder.view.bt_detail_kitab.setOnClickListener { listener.onClick( result ) }

    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){

    }

    fun setData(data: ArrayList<KitabModel.Result>){
        this.results.clear()
        this.results.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(results: KitabModel.Result)
    }


}