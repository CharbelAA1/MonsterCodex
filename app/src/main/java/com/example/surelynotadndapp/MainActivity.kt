package com.example.surelynotadndapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val monsters_codex_button = findViewById<Button>(R.id.monsters_codex_button)
        monsters_codex_button.setOnClickListener {
            val Intent = Intent(this, monster_codex_screen:: class.java)
            startActivity(Intent)



        }

    }
}