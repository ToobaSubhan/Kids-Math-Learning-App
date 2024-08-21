package com.example.kidsmathsgame

data class GameSession(
    val operation: String,
    val difficulty: String,
    val score: Int,
    val correct: Int,
    val wrong: Int,
    val accuracy: Int,
    val playedAt: String
)
