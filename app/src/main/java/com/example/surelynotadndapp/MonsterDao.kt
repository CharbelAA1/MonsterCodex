package com.example.surelynotadndapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface  MonsterDao {

    @Upsert
    suspend fun insertMonster(monster: MonsterEntity)

    @Delete
    suspend fun deleteMonster(monster: MonsterEntity)

    @Query("SELECT * FROM monsters ORDER BY monsterName ASC")
    fun getMonsterOrderedByName(): Flow<List<MonsterEntity>>


}