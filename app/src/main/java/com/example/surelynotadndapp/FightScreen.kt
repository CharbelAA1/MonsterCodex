package com.example.surelynotadndapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.map
import com.example.surelynotadndapp.MonsterEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList


class FightScreen  : AppCompatActivity() {
    private lateinit var monsterAdapter: MonsterCardAdapter
    private lateinit var monsterRecyclerView: RecyclerView

    private val monsterDao: MonsterDao by lazy {
        MonsterDatabase.getInstance(application).monsterDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fight_screen)

        val addMonsterButton = findViewById<Button>(R.id.addMonsterButton)
        monsterRecyclerView = findViewById(R.id.monsterRecyclerView)

        // Initialize the RecyclerView and Adapter
        monsterRecyclerView.layoutManager = LinearLayoutManager(this)
        monsterRecyclerView.setItemViewCacheSize(50);

        monsterAdapter = MonsterCardAdapter()
        monsterRecyclerView.adapter = monsterAdapter

        // When the "Add Monster" button is clicked, add a new card
        addMonsterButton.setOnClickListener {
            monsterAdapter.addMonsterCard()
        }

        // Load monsters' names from the database and update the adapter
        loadMonsterNamesFromDatabase()
    }

    private fun loadMonsterNamesFromDatabase() {
        val coroutineScope = CoroutineScope(Dispatchers.IO) // Use the appropriate dispatcher

        coroutineScope.launch {
            val monsterNames = monsterDao.getMonsterOrderedByName().map { monsterList ->
                monsterList.map { it.monsterName }
            }.first()

            withContext(Dispatchers.Main) {
                monsterAdapter.setMonsterNames(monsterNames)
            }
        }
    }
}