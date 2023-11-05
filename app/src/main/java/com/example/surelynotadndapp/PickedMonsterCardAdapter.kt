package com.example.surelynotadndapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class PickedMonsterCardAdapter : RecyclerView.Adapter<PickedMonsterCardAdapter.PickedMonsterCardViewHolder>() {

    init {
        Log.d("PickedMonsterCardAdapte", "Adapter created")
    }

    private val monsters: MutableList<MonsterEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickedMonsterCardViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.picked_monster_card, parent, false)
        return PickedMonsterCardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PickedMonsterCardViewHolder, position: Int) {
        val monster = monsters[position]
        holder.bind(monster)
        Log.d(
            "PickedMonsterCardAdapte",
            "onBindViewHolder: Bound monster at position $position, Monster Name: ${monster.monsterName}"
        )
    }

    override fun getItemCount(): Int {
        return monsters.size
    }

    fun addMonsterCard(monster: MonsterEntity) {
        monsters.add(monster)
        notifyDataSetChanged()
        Log.d("PickedMonsterCardAdapte", "addMonsterCard: Added a monster")
    }

    inner class PickedMonsterCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val monsterNameTextView: TextView = itemView.findViewById(R.id.monster_name_in_fight_screen)
        private val hpTextView: TextView = itemView.findViewById(R.id.hp_text_view)
        private val fightButton: Button = itemView.findViewById(R.id.fight_button_in_card)
        private val damageTakenTypeSpinner: Spinner = itemView.findViewById(R.id.damage_taken_type)
        private val damageTakenTextField: EditText = itemView.findViewById(R.id.damage_taken_text_field)

        private var itemPosition: Int = -1 // Store the position of the ViewHolder

        fun bind(monster: MonsterEntity) {
            itemPosition = adapterPosition

            monsterNameTextView.text = monster.monsterName
            hpTextView.text = "${monster.monsterHP}"

            val hpToRemoveText = hpTextView.text.toString() // Get the text from hpTextView as a String
            var currentHP: Int = hpToRemoveText.toInt()


            val damageTypes = arrayOf(
                "Acid",
                "Cold",
                "Fire",
                "Force",
                "Lightning",
                "Necrotic",
                "Poison",
                "Psychic",
                "Radiant",
                "Thunder",
                "Bludgeoning",
                "Slashing",
                "Piercing",
                "NonMagical melee",
                "Healing",
                "True Damage"
            )
            val adapter = ArrayAdapter(itemView.context, android.R.layout.simple_spinner_item, damageTypes)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            damageTakenTypeSpinner.adapter = adapter

            fightButton.setOnClickListener {
                val damageText = damageTakenTextField.text.toString()
                if (damageText.isNotEmpty()) {
                    val damageType = damageTakenTypeSpinner.selectedItem.toString() // Get selected damage type
                    val damage = damageText.toInt()

                    // Retrieve the damage value from the database
                    val damageValue = when (damageType) {
                        "Acid" -> monster.acid
                        "Fire" -> monster.fire
                        "Cold" -> monster.cold
                        "Force" -> monster.force
                        "Lightning" -> monster.lightning
                        "Necrotic" -> monster.necrotic
                        "Poison" -> monster.poison
                        "Psychic" -> monster.psychic
                        "Radiant" -> monster.radiant
                        "Thunder" -> monster.thunder
                        "Bludgeoning" -> monster.bludgeoning
                        "Slashing" -> monster.slashing
                        "Piercing" -> monster.piercing
                        "NonMagical melee" -> monster.nonMagicMelee
                        "Healing" -> "H"
                        "True Damage" -> "T"

                        else -> "None" // Default to "None" for unknown types
                    }
                    Log.d("PickedMonsterCardAdapte", "Damage Type: $damageType, Damage Value: $damageValue")

                    // Apply damage based on the retrieved value
                    when (damageValue) {
                        "R" -> currentHP -= damage / 2 // Resistance (half damage)
                        "I" -> currentHP -= 0 // Immunity (no damage)
                        "V" -> currentHP -= damage * 2 // Vulnerability (double damage)
                        "T" -> currentHP -= damage  // Vulnerability (double damage)
                        "H" -> currentHP += damage  // Vulnerability (double damage)
                        else -> currentHP -= damage // Normal damage
                    }

                    Log.d("MonsterCardAdapter", "current HP: $currentHP")
                    hpTextView.text = "$currentHP" // Update the UI

                    if (currentHP <= 0) {
                        // Remove the card when HP reaches 0 or below
                        removeCard()
                    }

                    damageTakenTextField.text.clear()
                }
            }
        }


        private fun removeCard() {
            if (itemPosition >= 0) {
                monsters.removeAt(itemPosition)
                notifyItemRemoved(itemPosition)
            }
        }
    }
}