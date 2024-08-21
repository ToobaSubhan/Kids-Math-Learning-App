package com.example.kidsmathsgame

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class DifficultyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)

        val operation = intent.getStringExtra("OPERATION") ?: "+"

        findViewById<View>(R.id.btnEasy).setOnClickListener {
            startGame(operation, "EASY")
        }

        findViewById<View>(R.id.btnMedium).setOnClickListener {
            startGame(operation, "MEDIUM")
        }

        findViewById<View>(R.id.btnHard).setOnClickListener {
            startGame(operation, "HARD")
        }
    }

    private fun startGame(operation: String, difficulty: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("cals", operation)
        intent.putExtra("DIFFICULTY", difficulty)
        startActivity(intent)
        finish()
    }
}
