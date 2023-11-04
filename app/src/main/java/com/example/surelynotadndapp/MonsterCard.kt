package com.example.surelynotadndapp

import android.util.Log
import android.view.View
import android.widget.TextView

data class MonsterCard(
    private val textView: TextView,
    val monsterEntity: MonsterEntity
) {
    private var currentHP: Int = monsterEntity.monsterHP

    init {
        textView.text = currentHP.toString()
    }

    fun attack(damageAmount: Int) {
        val updatedHP = currentHP - damageAmount

        if (updatedHP <= 0) {
            textView.text = "0"
            currentHP = 0
            textView.visibility = View.GONE
            Log.d("MonsterCard", "Monster defeated")
        } else {
            textView.text = updatedHP.toString()
            currentHP = updatedHP
        }
    }
}