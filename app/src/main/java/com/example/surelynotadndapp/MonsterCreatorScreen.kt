package com.example.surelynotadndapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MonsterCreatorScreen : AppCompatActivity() {
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<DamageType>
    private lateinit var monsterDatabase: MonsterDatabase
    private lateinit var monsterDao: MonsterDao

    lateinit var damaType: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monster_codex_screen)

        // Initialize Room database and MonsterDao
        monsterDatabase = Room.databaseBuilder(applicationContext, MonsterDatabase::class.java, "monster_database").build()
        monsterDao = monsterDatabase.monsterDao()

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

        newRecyclerView = findViewById(R.id.recyclerView_here)
        newRecyclerView.setItemViewCacheSize(25);
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
        val monsterName = findViewById<EditText>(R.id.monster_name_text_field_here)
        val monsterHP = findViewById<EditText>(R.id.monster_hp_text_field_here)
        val monsterNameText = monsterName.text.toString().trim()
        val monsterHPText = monsterHP.text.toString().trim()

        if (monsterNameText.isEmpty() || monsterNameText.length > 30) {
            Toast.makeText(this, "Monster Name must not be empty and should be 30 characters or less", Toast.LENGTH_SHORT).show()
            return
        }

        if (monsterHPText.isEmpty() || monsterHPText.length > 4) {
            Toast.makeText(this, "Monster HP must not be empty and should be 4 characters or less", Toast.LENGTH_SHORT).show()
            return
        }

        val monsterEntity = MonsterEntity(
            monsterName = monsterNameText,
            monsterHP = monsterHPText.toInt(),
            acid = getSelectedDamageType("Acid"),
            cold = getSelectedDamageType("Cold"),
            fire = getSelectedDamageType("Fire"),
            force = getSelectedDamageType("Force"),
            lightning = getSelectedDamageType("Lightning"),
            necrotic = getSelectedDamageType("Necrotic"),
            poison = getSelectedDamageType("Poison"),
            psychic = getSelectedDamageType("Psychic"),
            radiant = getSelectedDamageType("Radiant"),
            thunder = getSelectedDamageType("Thunder"),
            bludgeoning = getSelectedDamageType("Bludgeoning"),
            slashing = getSelectedDamageType("Slashing"),
            piercing = getSelectedDamageType("Piercing"),
            nonMagicMelee = getSelectedDamageType("NonMagicMelee")
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                monsterDao.insertMonster(monsterEntity)
                runOnUiThread {
                    Toast.makeText(this@MonsterCreatorScreen, "Monster data saved successfully", Toast.LENGTH_SHORT).show()
                }
                observeAndPrintMonsters()
            } catch (e: Exception) {
                // Handle the error (e.g., database is not created or other exceptions)
                Log.e("MonsterCreatorScreen", "Error saving monster data: ${e.message}")
                runOnUiThread {
                    Toast.makeText(this@MonsterCreatorScreen, "Error saving monster data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getSelectedDamageType(damageType: String): String {
        val selectedRadioButton = newArrayList.find { it.damageType == damageType }
        return selectedRadioButton?.selectedRadioButtonText ?: "None"
    }

    private fun observeAndPrintMonsters() {
        CoroutineScope(Dispatchers.IO).launch {
            monsterDao.getMonsterOrderedByName().collect { monsters ->
                for (monster in monsters) {
                    val damageTypes = getDamageTypesAsString(monster)
                    Log.d("MonsterCreatorScreen", "Monster Name: ${monster.monsterName}, HP: ${monster.monsterHP},  Damage Types: $damageTypes")
                    // You can print other monster properties here as needed
                }
            }
        }
    }
    private fun getDamageTypesAsString(monster: MonsterEntity): String {
        val damageTypeList = mutableListOf<String>()
        val damageTypes = arrayOf(
            "Acid", "Cold", "Fire", "Force", "Lightning", "Necrotic", "Poison",
            "Psychic", "Radiant", "Thunder", "Bludgeoning", "Slashing", "Piercing", "NonMagicMelee"
        )
        for (damageType in damageTypes) {
            val damageTypeText = when (damageType) {
                "Acid" -> monster.acid
                "Cold" -> monster.cold
                "Fire" -> monster.fire
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
                "NonMagicMelee" -> monster.nonMagicMelee
                else -> "None"
            }
            damageTypeList.add("$damageType: $damageTypeText")
        }
        return damageTypeList.joinToString(", ")
    }
}