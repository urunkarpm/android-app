package com.funcalc.app.domain.model

/**
 * Represents a fun fact that can be displayed to kids
 */
data class FunFact(
    val id: Long = 0,
    val fact: String,
    val category: FunFactCategory
)

/**
 * Categories for fun facts
 */
enum class FunFactCategory {
    SPACE,
    ANIMALS,
    NATURE,
    SCIENCE,
    HUMAN_BODY
}
