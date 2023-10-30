package com.example.surelynotadndapp.monsterdataScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.surelynotadndapp.MonsterDao
import com.example.surelynotadndapp.MonsterDatabase
import com.example.surelynotadndapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MonsterDataScreen : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var monsterAdapter: MonsterAdapter
    private lateinit var monsterDatabase: MonsterDatabase
    private lateinit var monsterDao: MonsterDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monster_data_screen)

        recyclerView = findViewById(R.id.recyclerViewMonsterDataScreen)
        recyclerView.layoutManager = LinearLayoutManager(this)

        monsterDatabase = Room.databaseBuilder(applicationContext, MonsterDatabase::class.java, "monster_database").build()
        monsterDao = monsterDatabase.monsterDao()

        // Retrieve monster data from the database and set it in the adapter
        CoroutineScope(Dispatchers.IO).launch {
            val monsters = monsterDao.getMonsterOrderedByName().first()
            runOnUiThread {
                monsterAdapter = MonsterAdapter(monsters)
                recyclerView.adapter = monsterAdapter
            }
        }

    }
    fun onMonsterCardClick(view: View) {
        // Get the monsterId of the clicked card
        val monsterId = view.getTag(R.string.monster_id_tag) as Int
        Log.d("MonsterDataScreen", "Card clicked, monsterId: $monsterId")

        // Start the MonsterDetails activity and pass the monsterId
        val intent = Intent(this, MonsterDetails::class.java)
        intent.putExtra("monsterId", monsterId)
        startActivity(intent)
    }


}