package com.example.kidsmathsgame

import android.graphics.Color

data class GameTheme(
    val bgTop: Int,
    val bgBottom: Int,
    val accentColor: Int,
    val operatorSymbol: String
)

object ThemeManager {
    fun getTheme(operation: String): GameTheme {
        return when (operation) {
            "+" -> GameTheme(
                bgTop = Color.parseColor("#1B5E20"),
                bgBottom = Color.parseColor("#0a1f0a"),
                accentColor = Color.parseColor("#4CAF50"),
                operatorSymbol = "+"
            )
            "-" -> GameTheme(
                bgTop = Color.parseColor("#0D47A1"),
                bgBottom = Color.parseColor("#050e2e"),
                accentColor = Color.parseColor("#2196F3"),
                operatorSymbol = "−"
            )
            "*" -> GameTheme(
                bgTop = Color.parseColor("#4A148C"),
                bgBottom = Color.parseColor("#150526"),
                accentColor = Color.parseColor("#9C27B0"),
                operatorSymbol = "×"
            )
            "/" -> GameTheme(
                bgTop = Color.parseColor("#B71C1C"),
                bgBottom = Color.parseColor("#2a0505"),
                accentColor = Color.parseColor("#F44336"),
                operatorSymbol = "÷"
            )
            else -> GameTheme(
                bgTop = Color.parseColor("#1a1a2e"),
                bgBottom = Color.parseColor("#16213e"),
                accentColor = Color.parseColor("#4CAF50"),
                operatorSymbol = ""
            )
        }
    }
}
