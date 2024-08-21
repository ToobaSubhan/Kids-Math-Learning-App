package com.example.kidsmathsgame

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val score = intent.getIntExtra("SCORE", 0)
        val correct = intent.getIntExtra("CORRECT", 0)
        val wrong = intent.getIntExtra("WRONG", 0)
        val accuracy = intent.getIntExtra("ACCURACY", 0)
        val bestScore = intent.getIntExtra("BEST_SCORE", 0)
        val operation = intent.getStringExtra("OPERATION") ?: "+"
        val difficulty = intent.getStringExtra("DIFFICULTY") ?: "EASY"

        val rootLayout = findViewById<View>(R.id.rootLayout)
        val tvFinalScore = findViewById<TextView>(R.id.tvFinalScore)
        val tvCorrect = findViewById<TextView>(R.id.tvCorrect)
        val tvWrong = findViewById<TextView>(R.id.tvWrong)
        val tvAccuracy = findViewById<TextView>(R.id.tvAccuracy)
        val tvBestScore = findViewById<TextView>(R.id.tvBestScore)
        val tvNewHighScore = findViewById<TextView>(R.id.tvNewHighScore)
        val btnPlayAgain = findViewById<MaterialButton>(R.id.btnPlayAgain)
        val btnMainMenu = findViewById<MaterialButton>(R.id.btnMainMenu)

        // Apply Theme
        val theme = ThemeManager.getTheme(operation)
        val gradient = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(theme.bgTop, theme.bgBottom)
        )
        rootLayout.background = gradient

        // Score animation
        ValueAnimator.ofInt(0, score).apply {
            duration = 1000
            addUpdateListener { tvFinalScore.text = it.animatedValue.toString() }
            start()
        }

        // Stats
        tvCorrect.text = "✓ $correct"
        tvWrong.text = "✗ $wrong"
        tvAccuracy.text = "$accuracy%"
        tvBestScore.text = "Your best: $bestScore"

        if (score >= bestScore && score > 0) {
            tvNewHighScore.visibility = View.VISIBLE
        }

        btnPlayAgain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("cals", operation)
            intent.putExtra("DIFFICULTY", difficulty)
            startActivity(intent)
            finish()
        }

        btnMainMenu.setOnClickListener {
            val intent = Intent(this, PlayActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}
