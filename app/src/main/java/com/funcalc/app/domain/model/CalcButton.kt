package com.funcalc.app.domain.model

/**
 * Calculator button types
 */
sealed class CalcButton {
    data class Number(val value: String) : CalcButton()
    data class Operator(val symbol: String, val operation: Operation) : CalcButton()
    object Equals : CalcButton()
    object Clear : CalcButton()
    object AllClear : CalcButton()
    object Decimal : CalcButton()
    object Surprise : CalcButton()
}

/**
 * Available mathematical operations
 */
enum class Operation {
    ADD, SUBTRACT, MULTIPLY, DIVIDE
}
