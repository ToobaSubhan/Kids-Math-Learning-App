package com.example.kidsmathsgame

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.random.Random

class ChallengeCompleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_complete)

        val score = intent.getIntExtra("SCORE", 0)
        val target = intent.getIntExtra("TARGET", 8)
        val streak = intent.getIntExtra("STREAK", 0)

        findViewById<TextView>(R.id.tvScore).text = "Score: $score/$target"
        findViewById<TextView>(R.id.tvStreakUpdate).text = "🔥 $streak Day Streak!"

        setupBadge(streak)
        startConfetti()

        findViewById<Button>(R.id.btnViewCalendar).setOnClickListener {
            startActivity(Intent(this, DailyChallengeActivity::class.java))
            finish()
        }

        findViewById<Button>(R.id.btnMainMenu).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setupBadge(streak: Int) {
        val tvBadge = findViewById<TextView>(R.id.tvBadgeName)
        val ivBadge = findViewById<ImageView>(R.id.ivBadge)

        val badge = when {
            streak >= 30 -> "Monthly Master 👑"
            streak >= 14 -> "2-Week Champion 🏆"
            streak >= 7 -> "Week Warrior ⚡"
            streak >= 3 -> "3-Day Starter 🌱"
            else -> "Keep Going! 🚀"
        }
        tvBadge.text = badge
    }

    private fun startConfetti() {
        val container = findViewById<ConstraintLayout>(R.id.confettiContainer)
        val colors = intArrayOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA)
        
        for (i in 0 until 20) {
            val dot = View(this)
            val size = Random.nextInt(15, 30)
            dot.layoutParams = ViewGroup.LayoutParams(size, size)
            dot.setBackgroundColor(colors.random())
            dot.x = Random.nextInt(0, resources.displayMetrics.widthPixels).toFloat()
            dot.y = -size.toFloat()
            container.addView(dot)

            ValueAnimator.ofFloat(0f, resources.displayMetrics.heightPixels.toFloat() + 100).apply {
                duration = Random.nextLong(1500, 3000)
                addUpdateListener { animator ->
                    dot.y = animator.animatedValue as Float
                    dot.rotation = animator.animatedFraction * 360
                }
                start()
            }
        }
    }
}
