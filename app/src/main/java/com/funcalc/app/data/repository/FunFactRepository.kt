package com.funcalc.app.data.repository

import com.funcalc.app.data.local.dao.FunFactDao
import com.funcalc.app.domain.model.FunFact
import com.funcalc.app.domain.model.FunFactCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for fun facts
 */
@Singleton
class FunFactRepository @Inject constructor(
    private val funFactDao: FunFactDao
) {
    fun getAllFunFacts(): Flow<List<FunFact>> =
        funFactDao.getAllFunFacts().map { entities ->
            entities.map { it.toDomain() }
        }

    suspend fun getRandomFunFact(): FunFact? =
        funFactDao.getRandomFunFact()?.toDomain()

    suspend fun getRandomFunFactByCategory(category: FunFactCategory): FunFact? =
        funFactDao.getRandomFunFactByCategory(category.name)?.toDomain()

    suspend fun getFunFactsCount(): Int = funFactDao.getFunFactsCount()
}
