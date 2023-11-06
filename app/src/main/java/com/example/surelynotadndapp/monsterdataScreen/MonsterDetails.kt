package com.example.surelynotadndapp.monsterdataScreen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surelynotadndapp.MainActivity
import com.example.surelynotadndapp.MonsterDatabase
import com.example.surelynotadndapp.MonsterEntity
import com.example.surelynotadndapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class MonsterDetails : AppCompatActivity()  {

    private var monsterId: Int = 0
    private lateinit var damageTypeRecyclerView: RecyclerView
    private lateinit var damageTypeAdapter: DamageTypeAdapter
    private val damageTypesList = listOf("Acid", "Cold", "Fire", "Force","Lightning","Necrotic","Poison","Psychic","Radiant","Thunder","Bludgeoning","slashing","Piercing","NonMagicMelee")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monster_details)

        monsterId = intent.getIntExtra("monsterId", 0)
        val saveButton: Button = findViewById(R.id.save_button1)
        val deleteButton: Button = findViewById(R.id.delete_button)

        damageTypeRecyclerView = findViewById(R.id.recyclerView_here)
        damageTypeAdapter = DamageTypeAdapter(damageTypesList, mutableListOf())

        damageTypeRecyclerView.adapter = damageTypeAdapter
        damageTypeRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        saveButton.setOnClickListener {
            saveMonsterDetails()
        }

        deleteButton.setOnClickListener {
            deleteMonster()
        }

        loadMonsterDetails(damageTypeAdapter)
    }

    private fun loadMonsterDetails(damageTypeAdapter: DamageTypeAdapter) {
        if (monsterId != 0) {
            GlobalScope.launch(Dispatchers.Main) {
                val repository = MonsterRepository(MonsterDatabase.getDatabase(this@MonsterDetails).monsterDao())
                val monster = repository.getMonsterById(monsterId).firstOrNull()

                if (monster != null) {
                    val nameTextField: EditText = findViewById(R.id.monster_name_text_field_here)
                    val hpTextField: EditText = findViewById(R.id.monster_hp_text_field_here)
                    val acTextField: EditText = findViewById(R.id.edit_ac_text_field)
                    nameTextField.setText(monster.monsterName)
                    hpTextField.setText(monster.monsterHP.toString())
                    acTextField.setText(monster.ac.toString())
                    val selectedDamageTypes = listOf(
                        monster.acid, monster.cold, monster.fire, monster.force,
                        monster.lightning, monster.necrotic, monster.poison, monster.psychic,
                        monster.radiant, monster.thunder, monster.bludgeoning, monster.slashing,
                        monster.piercing, monster.nonMagicMelee
                    )

                    damageTypeAdapter.setSelectedDamageTypes(*selectedDamageTypes.toTypedArray())
                }
            }
        }
    }

    private fun saveMonsterDetails() {
        val nameTextField: EditText = findViewById(R.id.monster_name_text_field_here)
        val hpTextField: EditText = findViewById(R.id.monster_hp_text_field_here)
        val acTextField: EditText = findViewById(R.id.edit_ac_text_field)
        val name = nameTextField.text.toString()
        val hp = hpTextField.text.toString().toInt()
        val ac = acTextField.text.toString().toInt()

        if (monsterId != 0) {
            val selectedDamageTypes = damageTypeAdapter.getSelectedDamageTypes()
            val updatedMonster = MonsterEntity(
                monsterId, name, hp,ac,
                selectedDamageTypes[0] ?: "None",
                selectedDamageTypes[1] ?: "None",
                selectedDamageTypes[2] ?: "None",
                selectedDamageTypes[3] ?: "None",
                selectedDamageTypes[4] ?: "None",
                selectedDamageTypes[5] ?: "None",
                selectedDamageTypes[6] ?: "None",
                selectedDamageTypes[7] ?: "None",
                selectedDamageTypes[8] ?: "None",
                selectedDamageTypes[9] ?: "None",
                selectedDamageTypes[10] ?: "None",
                selectedDamageTypes[11] ?: "None",
                selectedDamageTypes[12] ?: "None"
            )

            GlobalScope.launch(Dispatchers.IO) {
                val repository = MonsterRepository(MonsterDatabase.getDatabase(this@MonsterDetails).monsterDao())
                repository.updateMonster(updatedMonster)
                runOnUiThread {
                    Toast.makeText(this@MonsterDetails, "Monster details saved.", Toast.LENGTH_SHORT).show()
                    navigateBackAndReload()
                }
            }
        }
    }

    private fun deleteMonster() {
        if (monsterId != 0) {
            GlobalScope.launch(Dispatchers.IO) {
                val repository = MonsterRepository(MonsterDatabase.getDatabase(this@MonsterDetails).monsterDao())
                repository.deleteMonsterById(monsterId)
                runOnUiThread {
                    Toast.makeText(this@MonsterDetails, "Monster deleted.", Toast.LENGTH_SHORT).show()
                    navigateBackAndReload()
                }
            }
        }
    }

    private fun navigateBackAndReload() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}