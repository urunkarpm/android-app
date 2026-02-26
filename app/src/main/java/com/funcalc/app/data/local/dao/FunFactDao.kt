package com.funcalc.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.funcalc.app.data.local.entity.FunFactEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for fun facts
 */
@Dao
interface FunFactDao {
    @Query("SELECT * FROM fun_facts")
    fun getAllFunFacts(): Flow<List<FunFactEntity>>

    @Query("SELECT * FROM fun_facts ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomFunFact(): FunFactEntity?

    @Query("SELECT * FROM fun_facts WHERE category = :category ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomFunFactByCategory(category: String): FunFactEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFunFacts(facts: List<FunFactEntity>)

    @Query("SELECT COUNT(*) FROM fun_facts")
    suspend fun getFunFactsCount(): Int
}
