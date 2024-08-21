package com.example.kidsmathsgame

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.util.*

class DailyChallengeActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private var todayChallenge: DailyChallenge? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_challenge)

        db = DatabaseHelper(this)
        loadTodayChallenge()
        setupCalendar()

        findViewById<Button>(R.id.btnStartChallenge).setOnClickListener {
            todayChallenge?.let { challenge ->
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("MODE", "DAILY_CHALLENGE")
                intent.putExtra("cals", challenge.operation)
                intent.putExtra("TARGET", challenge.targetScore)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun loadTodayChallenge() {
        todayChallenge = db.getTodayChallenge()
        if (todayChallenge == null) {
            val ops = arrayOf("+", "-", "*", "/")
            val calendar = Calendar.getInstance()
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) // Sun=1, Mon=2...
            
            val op = when (dayOfWeek) {
                2 -> "+"
                3 -> "-"
                4 -> "*"
                5 -> "/"
                6 -> ops.random()
                else -> {
                    // Weekend: player's weakest
                    val weak = db.getWeakQuestions("+", 1) + 
                               db.getWeakQuestions("-", 1) + 
                               db.getWeakQuestions("*", 1) + 
                               db.getWeakQuestions("/", 1)
                    if (weak.isNotEmpty()) ops.random() else ops.random()
                }
            }
            todayChallenge = db.createTodayChallenge(op, 8)
        }

        val cardToday = findViewById<CardView>(R.id.cardToday)
        val tvOp = findViewById<TextView>(R.id.tvTodayOp)
        val tvStatus = findViewById<TextView>(R.id.tvStatus)
        val btnStart = findViewById<Button>(R.id.btnStartChallenge)
        val tvStreak = findViewById<TextView>(R.id.tvStreakVal)

        val opName = when(todayChallenge?.operation) {
            "+" -> "Addition ➕"
            "-" -> "Subtraction ➖"
            "*" -> "Multiplication ✖️"
            "/" -> "Division ➗"
            else -> ""
        }
        
        tvOp.text = "Today: $opName"
        tvStreak.text = "🔥 ${db.getCurrentStreak()}"

        if (todayChallenge?.completed == true) {
            tvStatus.text = "COMPLETED ✅"
            tvStatus.setTextColor(Color.parseColor("#4CAF50"))
            btnStart.text = "Practice More"
        } else {
            tvStatus.text = "NOT COMPLETED ⭕"
            tvStatus.setTextColor(Color.parseColor("#F44336"))
        }
    }

    private fun setupCalendar() {
        val grid = findViewById<androidx.gridlayout.widget.GridLayout>(R.id.calendarGrid)
        val history = db.getLast30Days()
        
        grid.removeAllViews()
        for (challenge in history) {
            val view = View(this)
            val params = androidx.gridlayout.widget.GridLayout.LayoutParams()
            params.width = 40.dpToPx()
            params.height = 40.dpToPx()
            params.setMargins(4.dpToPx(), 4.dpToPx(), 4.dpToPx(), 4.dpToPx())
            view.layoutParams = params

            val shape = GradientDrawable()
            shape.shape = GradientDrawable.OVAL
            
            when {
                challenge == null -> shape.setColor(Color.LTGRAY)
                challenge.completed -> shape.setColor(Color.parseColor("#4CAF50"))
                challenge.achievedScore > 0 -> shape.setColor(Color.parseColor("#FFEB3B"))
                else -> shape.setColor(Color.parseColor("#F44336"))
            }
            
            view.background = shape
            grid.addView(view)
        }
    }

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()
}
