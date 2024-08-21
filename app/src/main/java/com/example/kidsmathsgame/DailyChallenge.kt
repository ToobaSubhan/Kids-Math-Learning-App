package com.example.kidsmathsgame

data class DailyChallenge(
    val id: Int,
    val challengeDate: String,
    val operation: String,
    val targetScore: Int,
    val achievedScore: Int,
    val completed: Boolean,
    val streakDay: Int
)
