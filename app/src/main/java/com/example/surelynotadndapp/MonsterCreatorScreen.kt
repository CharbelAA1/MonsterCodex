package com.example.surelynotadndapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MonsterCreatorScreen : AppCompatActivity() {
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<DamageType>
    lateinit var damaType: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monster_codex_screen)
        initiateDamageType()
    }

    private fun initiateDamageType() {
        damaType = arrayOf(
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
            "slashing",
            "Piercing",
            "NonMagicMelee"
        )

        newRecyclerView = findViewById(R.id.recyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<DamageType>()
        getUserdata()
    }

    private fun getUserdata() {
        for (i in damaType.indices) {
            val damType = DamageType(damaType[i])
            damType.selectedRadioButtonText = "None"  // Set the default text to "None"
            damType.selectedRadioButtonId = R.id.None  // Set the default radio button ID to "None"
            newArrayList.add(damType)

            // Log the data being added to newArrayList
            Log.d("MonsterCreatorScreen", "Added to newArrayList: ${damType.damageType}")
        }

        newRecyclerView.adapter = MyAdapter(newArrayList, object : MyAdapter.OnRadioButtonSelectedListener {
            override fun onRadioButtonSelected(position: Int, selectedRadioButtonId: Int) {
                // Handle Radio Button selection if needed
                newArrayList[position].selectedRadioButtonText = when (selectedRadioButtonId) {
                    R.id.None -> "N/A"
                    R.id.Vulnerability -> "V"
                    R.id.Resistance -> "R"
                    R.id.immunity -> "I"
                    else -> "None"
                }
                newArrayList[position].selectedRadioButtonId = selectedRadioButtonId
            }
        })
    }

    fun saveDataMonster(view: View) {
        val monsterName = findViewById<EditText>(R.id.monster_name_text_field)
        val monsterHP = findViewById<EditText>(R.id.monster_hp_text_field)
        val monsterNameText = monsterName.text.toString().trim()
        val monsterHPText = monsterHP.text.toString().trim()

        // Validate the monster name
        if (monsterNameText.isEmpty() || monsterNameText.length > 30) {
            Toast.makeText(this, "Monster Name must not be empty and should be 30 characters or less", Toast.LENGTH_SHORT).show()
            return
        }

        // Validate the monster HP
        if (monsterHPText.isEmpty() || monsterHPText.length > 4) {
            Toast.makeText(this, "Monster HP must not be empty and should be 4 characters or less", Toast.LENGTH_SHORT).show()
            return
        }
        val adapter = newRecyclerView.adapter as? MyAdapter

        if (adapter != null) {
            // Iterate through the RecyclerView items
            for (i in 0 until newArrayList.size) {
                val damageType = newArrayList[i].damageType
                val selectedRadioButtonText = newArrayList[i].selectedRadioButtonText
                // Print the values
                Log.d("MonsterCreatorScreen", "Damage Type: $damageType")
                Log.d("MonsterCreatorScreen", "Selected RadioButton Text: $selectedRadioButtonText")
            }
        }

        // Print the monster name and HP
        Log.d("MonsterCreatorScreen", "Monster Name: ${monsterName.text}")
        Log.d("MonsterCreatorScreen", "Monster HP: ${monsterHP.text}")
    }
}