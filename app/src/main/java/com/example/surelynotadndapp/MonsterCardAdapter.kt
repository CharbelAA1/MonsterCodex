package com.example.surelynotadndapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView

class MonsterCardAdapter(
    private val onMonsterSelectedListener: (String?) -> Unit,
) : RecyclerView.Adapter<MonsterCardAdapter.MonsterCardViewHolder>() {


    private val monsters: MutableList<MonsterEntity> = mutableListOf()
    private var monsterNames: List<String> = emptyList()
    private val selectedMonsterNames: MutableSet<String> = mutableSetOf()
    val selectedNamesFromSpinner: MutableList<String> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterCardViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_monster_card, parent, false)
        return MonsterCardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MonsterCardViewHolder, position: Int) {
        val monster = monsters[position]

        if (selectedNamesFromSpinner.size - 1 < position) {
            selectedNamesFromSpinner.add(monsterNames[0])
        }

        holder.bind(monster, monsterNames)
    }

    override fun getItemCount(): Int {
        return monsters.size
    }

    fun addMonsterCard() {
        monsters.add(MonsterEntity(0, "", 0,0))
        notifyDataSetChanged()
    }

    fun setMonsterNames(names: List<String>) {
        monsterNames = names
        notifyDataSetChanged()
    }

    fun clearMonsters() {
        monsters.clear()
        notifyDataSetChanged()
    }

    inner class MonsterCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val monsterSpinner: Spinner = itemView.findViewById(R.id.monsterSpinner)
        private var selectedMonsterName: String? = null

        init {
            monsterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedMonsterName = monsterNames[position]
                    selectedMonsterNames.add(selectedMonsterName)
                    onMonsterSelectedListener(selectedMonsterName)
                    selectedNamesFromSpinner.set(
                        this@MonsterCardViewHolder.adapterPosition,
                        selectedMonsterName
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle deselection if needed
                }
            }
        }

        fun bind(monster: MonsterEntity, names: List<String>) {
            // Set up the UI elements in your item_monster_card.xml layout
            val spinnerAdapter =
                ArrayAdapter(itemView.context, android.R.layout.simple_spinner_item, names)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            monsterSpinner.adapter = spinnerAdapter
            monsterSpinner.isEnabled = true
        }

        // Getter to retrieve the selected monster name
        fun getSelectedMonsterName(): String? {
            return selectedMonsterName
        }
    }
}