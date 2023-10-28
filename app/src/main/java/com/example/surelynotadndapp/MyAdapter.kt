package com.example.surelynotadndapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (private val newlist : ArrayList<DamageType>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        val currentItem = newlist[position]
        holder.damageType.text = currentItem.damageType
    }

    override fun getItemCount(): Int {

        return newlist.size

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val damageType : TextView = itemView.findViewById(R.id.damage_type)
    }

}