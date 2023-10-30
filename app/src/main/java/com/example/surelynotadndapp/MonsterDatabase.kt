package com.example.surelynotadndapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MonsterEntity::class], version = 1)
abstract class MonsterDatabase : RoomDatabase(){
    abstract fun monsterDao(): MonsterDao

    companion object {
        @Volatile
        private var INSTANCE: MonsterDatabase? = null

        fun getDatabase(context: Context): MonsterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MonsterDatabase::class.java,
                    "monster_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}