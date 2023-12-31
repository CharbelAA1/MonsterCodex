package com.example.surelynotadndapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.surelynotadndapp.monsterdataScreen.MonsterDataScreen
import java.io.File

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
        setContentView(R.layout.activity_main)
    }

    fun goToMonsterCreatorScreen(view: View) {
        val intent = Intent(this, MonsterCreatorScreen::class.java)
        startActivity(intent)
    }

    fun goToMonsterDataScreen(view: View) {
        val intent = Intent(this, MonsterDataScreen::class.java)
        startActivity(intent)
    }

    fun goToFightScreen(view: View) {
        val intent = Intent(this, FightScreen::class.java)
        startActivity(intent)
    }
}