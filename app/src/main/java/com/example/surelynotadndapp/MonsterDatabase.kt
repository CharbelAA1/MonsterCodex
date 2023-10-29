package com.example.surelynotadndapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MonsterEntity::class], version = 1)
abstract class MonsterDatabase : RoomDatabase(){
    abstract fun monsterDao(): MonsterDao
}