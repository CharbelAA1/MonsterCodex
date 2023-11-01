package com.example.surelynotadndapp.monsterdataScreen

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.surelynotadndapp.R

class DamageTypeAdapter(
    private val damageTypes: List<String>,
    private val selectedDamageTypes: List<String?>
) : RecyclerView.Adapter<DamageTypeAdapter.ViewHolder>() {

    private val selectedPositions = IntArray(damageTypes.size)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val damageTypeTextView: TextView = itemView.findViewById(R.id.damage_type2)
        val radioButtons: List<RadioButton> = listOf(
            itemView.findViewById(R.id.None2),
            itemView.findViewById(R.id.Vulnerability2),
            itemView.findViewById(R.id.Resistance2),
            itemView.findViewById(R.id.Immunity2)
        )

        init {
            for (i in radioButtons.indices) {
                radioButtons[i].setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        selectedPositions[position] = i
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.monster_details_to_change, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return damageTypes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val damageType = damageTypes[position]
        holder.damageTypeTextView.text = damageType

        val selectedPosition = selectedPositions[position]

        for (i in holder.radioButtons.indices) {
            holder.radioButtons[i].isChecked = i == selectedPosition
        }
    }

    fun setSelectedDamageTypes(vararg selectedTypes: String?) {
        for (i in selectedTypes.indices) {
            if (i < selectedPositions.size) {
                selectedPositions[i] = getPositionForDamageType(selectedTypes[i], selectedDamageTypes)
            } else {
                // Handle the case where the index is out of bounds
                // You can log an error or take appropriate action
            }
        }
        notifyDataSetChanged()
    }

    private fun getPositionForDamageType(damageType: String?, selectedDamageTypes: List<String?>): Int {
        // Your mapping logic remains the same
        when (damageType?.trim()) {
            "R" -> return 3
            "I" -> return 4
            "V" -> return 2
            "N/A" -> return 0
            else -> return 0
        }
    }

    fun getSelectedDamageTypes(): List<String?> {
        val selectedDamageTypes = mutableListOf<String?>()
        for (position in selectedPositions) {
            when (position) {
                3 -> selectedDamageTypes.add("R")
                2 -> selectedDamageTypes.add("V")
                4 -> selectedDamageTypes.add("I")
                else -> selectedDamageTypes.add("None") // Default to null for unselected or unrecognized types
            }
        }
        return selectedDamageTypes
    }
}