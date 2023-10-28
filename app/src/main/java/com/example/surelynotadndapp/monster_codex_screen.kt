package com.example.surelynotadndapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class monster_codex_screen : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var  newArrayList: ArrayList<DamageType>
    lateinit var damaType: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monster_codex_screen)


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
        for(i in damaType.indices){
            val damType = DamageType(damaType[i])
            newArrayList.add(damType)
        }

        newRecyclerView.adapter = MyAdapter(newArrayList)
    }
}