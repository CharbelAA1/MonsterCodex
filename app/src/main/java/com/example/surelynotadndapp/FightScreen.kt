package com.example.surelynotadndapp

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class FightScreen  : AppCompatActivity() {
    private lateinit var monsterAdapter: MonsterCardAdapter
    private lateinit var pickedMonsterAdapter: PickedMonsterCardAdapter

    private lateinit var monsterRecyclerView: RecyclerView

    private val selectedMonsters: MutableList<String> = mutableListOf()
    private val monsterNames: MutableList<String> = mutableListOf()

    private val addMonsterButtonEnabled: Boolean = true
    private var lockInButtonEnabled: Boolean = false

    private val monsterDao: MonsterDao by lazy {
        MonsterDatabase.getInstance(application).monsterDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fight_screen)

        val addMonsterButton = findViewById<Button>(R.id.addMonsterButton)
        val lockInButton = findViewById<Button>(R.id.lockInButton)
        val clearRecyclerViewButton = findViewById<Button>(R.id.clear_recyclerview)

        monsterRecyclerView = findViewById(R.id.monsterRecyclerView)

        // Initialize the RecyclerView and Adapters
        monsterRecyclerView.layoutManager = LinearLayoutManager(this)
        monsterRecyclerView.setItemViewCacheSize(50)

        monsterAdapter = MonsterCardAdapter { selectedMonsterName ->
            // Handle the selected monster name
            Log.d("MonsterCardAdapter", "Selected Monster: $selectedMonsterName")
        }

        pickedMonsterAdapter = PickedMonsterCardAdapter()  // Initialize the adapter
        monsterRecyclerView.adapter = pickedMonsterAdapter
        monsterRecyclerView.adapter = monsterAdapter

        addMonsterButton.setOnClickListener {
            // Add a new card when the "Add Monster" button is clicked
            monsterAdapter.addMonsterCard()
            lockInButtonEnabled = true
            updateButtonStates()
        }

        lockInButton.setOnClickListener {
            // Lock in selected monsters when the "Lock In" button is pressed
            lockInSelectedMonsters()
            lockInButtonEnabled = false
            updateButtonStates()
        }

        clearRecyclerViewButton.setOnClickListener {
            // Clear the RecyclerView and enable "Add Monster" button when "Clear Fight" button is pressed
            selectedMonsters.clear()
            monsterAdapter.clearMonsters()
            lockInButtonEnabled = false // Disable the "Lock In" button after clearing
            updateButtonStates()
        }

        // Load monsters' names from the database and update the adapter
        loadMonsterNamesFromDatabase()
        updateButtonStates()
    }

    private fun updateButtonStates() {
        val addMonsterButton = findViewById<Button>(R.id.addMonsterButton)
        val lockInButton = findViewById<Button>(R.id.lockInButton)

        addMonsterButton.isEnabled = addMonsterButtonEnabled // Enable "Add Monster" button when enabled
        lockInButton.isEnabled = lockInButtonEnabled // Enable "Lock In" button when enabled
    }

    private fun loadMonsterNamesFromDatabase() {
        val coroutineScope = CoroutineScope(Dispatchers.IO)

        coroutineScope.launch {
            val monsterNames = monsterDao.getMonsterOrderedByName().map { monsterList ->
                monsterList.map { it.monsterName }
            }.first()

            withContext(Dispatchers.Main) {
                this@FightScreen.monsterNames.clear()
                this@FightScreen.monsterNames.addAll(monsterNames)
                monsterAdapter.setMonsterNames(monsterNames)
            }
        }
    }

    private fun lockInSelectedMonsters() {
        monsterRecyclerView.adapter = pickedMonsterAdapter

        // Fetch HP for the selected monsters from the database and add them to the PickedMonsterCardAdapter
        for (selectedMonsterName in monsterAdapter.selectedNamesFromSpinner) {
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            coroutineScope.launch {
                val selectedMonster = monsterDao.getMonsterByName(selectedMonsterName).firstOrNull()
                Log.d("LockIn", "After Locking Selection: $selectedMonsterName, HP: ${selectedMonster?.monsterHP}")
                withContext(Dispatchers.Main) {
                    if (selectedMonster != null) {
                        pickedMonsterAdapter.addMonsterCard(selectedMonster)
                    }
                }
            }
        }
    }

}