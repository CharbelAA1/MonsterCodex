package com.example.surelynotadndapp.monsterdataScreen

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.surelynotadndapp.MonsterEntity
import com.example.surelynotadndapp.R

class MonsterAdapter(private val monsterList: List<MonsterEntity>) :
    RecyclerView.Adapter<MonsterAdapter.MonsterViewHolder>() {

    class MonsterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView) // Reference the CardView here
        val monsterNameTextView: TextView = itemView.findViewById(R.id.Monster_Name_here)
        val monsterHPTextView: TextView = itemView.findViewById(R.id.Monster_hp_here)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.monster_cell, parent, false)
        return MonsterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MonsterViewHolder, position: Int) {
        val currentMonster = monsterList[position]
        holder.monsterNameTextView.text = currentMonster.monsterName
        holder.monsterHPTextView.text = currentMonster.monsterHP.toString()

        holder.cardView.setTag(R.string.monster_id_tag, currentMonster.id)
        // Add click handling for the cardView
        holder.cardView.setOnClickListener {
            // Handle click event here, e.g., open monster details screen
            val intent = Intent(holder.cardView.context, MonsterDetails::class.java)
            intent.putExtra("monsterId", currentMonster.id)
            holder.cardView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = monsterList.size
}