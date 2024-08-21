package com.example.kidsmathsgame

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var rvHistory: RecyclerView
    private lateinit var tvEmptyState: TextView
    private lateinit var bestScoresGrid: androidx.gridlayout.widget.GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        db = DatabaseHelper(this)
        rvHistory = findViewById(R.id.rvHistory)
        tvEmptyState = findViewById(R.id.tvEmptyState)
        bestScoresGrid = findViewById(R.id.bestScoresGrid)

        findViewById<ImageButton>(R.id.btnClearHistory).setOnClickListener {
            showClearConfirmation()
        }

        refreshData()
    }

    private fun refreshData() {
        val sessions = db.getRecentSessions(50)
        if (sessions.isEmpty()) {
            rvHistory.visibility = View.GONE
            tvEmptyState.visibility = View.VISIBLE
        } else {
            rvHistory.visibility = View.VISIBLE
            tvEmptyState.visibility = View.GONE
            rvHistory.layoutManager = LinearLayoutManager(this)
            rvHistory.adapter = HistoryAdapter(sessions)
        }

        populateBestScores()
    }

    private fun populateBestScores() {
        bestScoresGrid.removeAllViews()
        val ops = arrayOf("+", "-", "*", "/")
        val emojis = arrayOf("➕", "➖", "✖️", "➗")
        
        for (i in ops.indices) {
            val best = getMaxBestScore(ops[i])
            val card = createBestScoreCard(emojis[i], best)
            
            val params = androidx.gridlayout.widget.GridLayout.LayoutParams()
            params.width = 0
            params.height = androidx.gridlayout.widget.GridLayout.LayoutParams.WRAP_CONTENT
            params.columnSpec = androidx.gridlayout.widget.GridLayout.spec(androidx.gridlayout.widget.GridLayout.UNDEFINED, 1f)
            params.setMargins(8, 8, 8, 8)
            card.layoutParams = params
            
            bestScoresGrid.addView(card)
        }
    }

    private fun getMaxBestScore(op: String): Int {
        val difficulties = arrayOf("EASY", "MEDIUM", "HARD")
        var max = 0
        for (diff in difficulties) {
            val score = db.getHighScore(op, diff)
            if (score > max) max = score
        }
        return max
    }

    private fun createBestScoreCard(emoji: String, score: Int): View {
        val view = layoutInflater.inflate(android.R.layout.simple_list_item_2, null)
        val text1 = view.findViewById<TextView>(android.R.id.text1)
        val text2 = view.findViewById<TextView>(android.R.id.text2)
        
        text1.text = emoji
        text1.textSize = 20sp
        text1.setTextColor(Color.WHITE)
        
        text2.text = score.toString()
        text2.textSize = 18sp
        text2.textStyle = android.graphics.Typeface.BOLD
        text2.setTextColor(Color.WHITE)
        
        return view
    }
    
    // Extension property for sp to px conversion
    private val Int.sp: Float get() = this * resources.displayMetrics.scaledDensity

    private fun showClearConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Clear History")
            .setMessage("Delete all game history and high scores?")
            .setPositiveButton("Clear") { _, _ ->
                db.clearAllData()
                refreshData()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
