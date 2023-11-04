package com.example.surelynotadndapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class MonsterCardAdapter: RecyclerView.Adapter<MonsterCardAdapter.MonsterCardViewHolder>() {

    private val monsters: MutableList<MonsterEntity> = mutableListOf()
    private var monsterNames: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterCardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_monster_card, parent, false)
        return MonsterCardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MonsterCardViewHolder, position: Int) {
        val monster = monsters[position]
        holder.bind(monster, monsterNames)
    }

    override fun getItemCount(): Int {
        return monsters.size
    }

    fun addMonsterCard() {
        monsters.add(MonsterEntity(0, "", 0))
        notifyDataSetChanged()
    }

    fun setMonsterNames(names: List<String>) {
        monsterNames = names
        notifyDataSetChanged()
    }

    inner class MonsterCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val monsterSpinner: Spinner = itemView.findViewById(R.id.monsterSpinner)
        private val lockInButton: Button = itemView.findViewById(R.id.lockInButton)

        fun bind(monster: MonsterEntity, names: List<String>) {
            // Set up the UI elements in your item_monster_card.xml layout
            val spinnerAdapter = ArrayAdapter(itemView.context, android.R.layout.simple_spinner_item, names)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            monsterSpinner.adapter = spinnerAdapter

            lockInButton.setOnClickListener {
                // Handle the lock-in action if needed
            }
        }
    }
}