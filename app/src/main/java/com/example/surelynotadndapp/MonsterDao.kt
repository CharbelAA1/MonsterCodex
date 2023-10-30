package com.example.surelynotadndapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface  MonsterDao {

    @Upsert
    suspend fun insertMonster(monster: MonsterEntity)

    @Query("SELECT * FROM monsters ORDER BY monsterName ASC")
    fun getMonsterOrderedByName(): Flow<List<MonsterEntity>>

    @Query("SELECT * FROM monsters WHERE id = :monsterId")
    fun getMonsterById(monsterId: Int): Flow<MonsterEntity>

    @Query("DELETE FROM monsters WHERE id = :monsterId")
    suspend fun deleteMonsterById(monsterId: Int)

    @Update
    suspend fun updateMonster(monster: MonsterEntity)


    @Query("SELECT * FROM monsters")
    fun getAllMonsters(): Flow<List<MonsterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(monster: MonsterEntity)

    @Delete
    suspend fun delete(monster: MonsterEntity)

    @Update
    suspend fun update(monster: MonsterEntity)




}