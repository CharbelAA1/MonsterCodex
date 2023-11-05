package com.example.surelynotadndapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MonsterEntity::class], version = 2, exportSchema = false)
abstract class MonsterDatabase : RoomDatabase() {
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

        fun getInstance(context: Context): MonsterDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MonsterDatabase::class.java,
                        "monster_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}