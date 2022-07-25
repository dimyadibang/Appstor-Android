package com.mahadalynj.appstor.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahadalynj.appstor.R
import com.mahadalynj.appstor.data.model.UstadzModel
import kotlinx.android.synthetic.main.item_list_ust.view.*

class UstadzAdapter(var results: ArrayList<UstadzModel.DataUst>, val listener: OnAdapterListener):
    RecyclerView.Adapter<UstadzAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from( parent.context ).inflate( R.layout.item_list_ust,
            parent, false)
    )

    override fun getItemCount() = results.size
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.view.tv_nama_ust.text = result.name
        holder.view.tv_nama_hp.text = result.phone
        Glide.with(holder.view)
            .load(result.profile_pic)
            .placeholder(R.drawable.img_fotokosong)
            .error(R.drawable.img_fotokosong)
            .centerCrop()
            .into(holder.view.imageView_ust)
        holder.view.setOnClickListener { listener.onClick( result) }
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(data: List<UstadzModel.DataUst>){
        this.results.clear()
        this.results.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(result: UstadzModel.DataUst)
    }
}