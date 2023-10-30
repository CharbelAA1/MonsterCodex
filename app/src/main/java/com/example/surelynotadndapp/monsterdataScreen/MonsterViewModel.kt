package com.example.surelynotadndapp.monsterdataScreen

import androidx.lifecycle.*
import com.example.surelynotadndapp.MonsterEntity
import kotlinx.coroutines.flow.Flow


class MonsterViewModel(private val repository: MonsterRepository) : ViewModel() {

    val allMonsters: Flow<List<MonsterEntity>> = repository.allMonsters

    suspend fun insert(monster: MonsterEntity) {
        repository.insertMonster(monster)
    }

    suspend fun update(monster: MonsterEntity) {
        repository.updateMonster(monster)
    }

    suspend fun deleteMonsterById(monsterId: Int) {
        repository.deleteMonsterById(monsterId)
    }

    fun getMonsterById(monsterId: Int): Flow<MonsterEntity> {
        return repository.getMonsterById(monsterId)
    }
}

class MonsterViewModelFactory(private val repository: MonsterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MonsterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MonsterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}