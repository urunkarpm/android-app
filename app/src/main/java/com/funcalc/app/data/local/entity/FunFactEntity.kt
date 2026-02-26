package com.funcalc.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.funcalc.app.domain.model.FunFact
import com.funcalc.app.domain.model.FunFactCategory

/**
 * Room entity for storing fun facts
 */
@Entity(tableName = "fun_facts")
data class FunFactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fact: String,
    val category: String
) {
    fun toDomain(): FunFact = FunFact(
        id = id,
        fact = fact,
        category = try {
            FunFactCategory.valueOf(category)
        } catch (e: Exception) {
            FunFactCategory.SCIENCE
        }
    )

    companion object {
        fun fromDomain(funFact: FunFact): FunFactEntity = FunFactEntity(
            id = funFact.id,
            fact = funFact.fact,
            category = funFact.category.name
        )
    }
}
