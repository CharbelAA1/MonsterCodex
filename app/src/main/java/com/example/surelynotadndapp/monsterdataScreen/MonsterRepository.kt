package com.example.surelynotadndapp.monsterdataScreen

import androidx.lifecycle.LiveData
import com.example.surelynotadndapp.MonsterDao
import com.example.surelynotadndapp.MonsterEntity
import kotlinx.coroutines.flow.Flow


class MonsterRepository(private val monsterDao: MonsterDao) {

    val allMonsters: Flow<List<MonsterEntity>> = monsterDao.getAllMonsters()

    suspend fun insertMonster(monster: MonsterEntity) {
        monsterDao.insertMonster(monster)
    }

    suspend fun updateMonster(monster: MonsterEntity) {
        monsterDao.updateMonster(monster)
    }

    suspend fun deleteMonsterById(monsterId: Int) {
        monsterDao.deleteMonsterById(monsterId)
    }

    fun getMonsterById(monsterId: Int): Flow<MonsterEntity> {
        return monsterDao.getMonsterById(monsterId)
    }
}