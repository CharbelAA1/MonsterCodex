package com.example.surelynotadndapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (private val newlist: List<DamageType>, private val onRadioButtonSelectedListener: OnRadioButtonSelectedListener) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val damageType: TextView = itemView.findViewById(R.id.damage_type)
        val radioGroup: RadioGroup = itemView.findViewById(R.id.radioGroup)
    }

    interface OnRadioButtonSelectedListener {
        fun onRadioButtonSelected(position: Int, selectedRadioButtonId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newlist[position]
        holder.damageType.text = currentItem.damageType

        // Set the selected radio button based on the values in DamageType
        holder.radioGroup.check(currentItem.selectedRadioButtonId)

        holder.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            currentItem.selectedRadioButtonId = checkedId
            val selectedRadioButton = holder.itemView.findViewById<RadioButton>(checkedId)
            val radioButtonText = selectedRadioButton.text.toString()
            currentItem.selectedRadioButtonText = radioButtonText
            onRadioButtonSelectedListener.onRadioButtonSelected(position, checkedId)
        }
    }

    override fun getItemCount(): Int {
        return newlist.size
    }
}