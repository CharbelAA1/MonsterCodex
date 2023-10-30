package com.example.surelynotadndapp.monsterdataScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.surelynotadndapp.MonsterDatabase
import com.example.surelynotadndapp.MonsterEntity
import com.example.surelynotadndapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
class MonsterDetails : AppCompatActivity() {

    private var monsterId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monster_details)

        // Get the monsterId from the intent
        monsterId = intent.getIntExtra("monsterId", 0)

        loadMonsterDetails()

        val saveButton: Button = findViewById(R.id.save_button)
        saveButton.setOnClickListener {
            saveMonsterDetails()
        }

        val deleteButton: Button = findViewById(R.id.delete_button)
        deleteButton.setOnClickListener {
            deleteMonster()
        }
    }

    private fun loadMonsterDetails() {
        if (monsterId != 0) {
            GlobalScope.launch(Dispatchers.Main) {
                val repository = MonsterRepository(MonsterDatabase.getDatabase(this@MonsterDetails).monsterDao())
                val monster = repository.getMonsterById(monsterId).firstOrNull()

                if (monster != null) {
                    val nameTextField: EditText = findViewById(R.id.monster_name_text_field_here)
                    val hpTextField: EditText = findViewById(R.id.monster_hp_text_field_here)
                    nameTextField.setText(monster.monsterName)
                    hpTextField.setText(monster.monsterHP.toString())
                }
            }
        }
    }

    private fun saveMonsterDetails() {
        val nameTextField: EditText = findViewById(R.id.monster_name_text_field_here)
        val hpTextField: EditText = findViewById(R.id.monster_hp_text_field_here)
        val name = nameTextField.text.toString()
        val hp = hpTextField.text.toString().toInt()

        if (monsterId != 0) {
            val updatedMonster = MonsterEntity(monsterId, name, hp, /* other fields */)
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
        val intent = Intent(this, MonsterDataScreen::class.java) // Replace with the actual previous activity
        startActivity(intent)
        finish()
    }
}