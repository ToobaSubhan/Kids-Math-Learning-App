package com.example.kidsmathsgame

import kotlin.random.Random

class AdaptiveQuestionGenerator(
    private val db: DatabaseHelper,
    private val operation: String,
    private val difficulty: String
) {
    fun generate(): Question {
        val hasData = db.hasEnoughData(operation)
        val useAdaptive = hasData && Random.nextFloat() < 0.7f

        if (useAdaptive) {
            val weakOnes = db.getWeakQuestions(operation, 10)
            if (weakOnes.isNotEmpty()) {
                val picked = weakOnes.random()
                return Question(picked.first, picked.second, operation, true)
            }
        }

        // Exploration: Random new question
        return generateRandomQuestion()
    }

    private fun generateRandomQuestion(): Question {
        val range = when (difficulty) {
            "EASY" -> 1..10
            "MEDIUM" -> 1..20
            "HARD" -> 1..50
            else -> 1..10
        }

        var n1 = range.random()
        var n2 = range.random()

        when (operation) {
            "-" -> if (n1 < n2) { val temp = n1; n1 = n2; n2 = temp }
            "/" -> {
                // Ensure exact divisor
                val divisorRange = when (difficulty) {
                    "EASY" -> 1..5
                    "MEDIUM" -> 1..10
                    "HARD" -> 1..15
                    else -> 1..5
                }
                n2 = divisorRange.random()
                n1 = n2 * range.random()
            }
        }

        return Question(n1, n2, operation, false)
    }

    data class Question(val n1: Int, val n2: Int, val op: String, val isAdaptive: Boolean) {
        val answer: Int
            get() = when (op) {
                "+" -> n1 + n2
                "-" -> n1 - n2
                "*" -> n1 * n2
                "/" -> n1 / n2
                else -> 0
            }
    }
}
