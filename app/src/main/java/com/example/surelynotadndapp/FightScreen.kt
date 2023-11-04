package com.example.surelynotadndapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import com.example.surelynotadndapp.MonsterEntity
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList


class FightScreen  : AppCompatActivity() {
    private lateinit var monsterAdapter: MonsterCardAdapter
    private lateinit var monsterRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fight_screen)

        val addMonsterButton = findViewById<Button>(R.id.addMonsterButton)
        monsterRecyclerView = findViewById(R.id.monsterRecyclerView)

        // Initialize the RecyclerView and Adapter
        monsterRecyclerView.layoutManager = LinearLayoutManager(this)
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
        // Fetch monster names from the database
        val monsterNames = listOf("Monster 1", "Monster 2", "Monster 3") // Replace with your database query

        monsterAdapter.setMonsterNames(monsterNames)
    }
}