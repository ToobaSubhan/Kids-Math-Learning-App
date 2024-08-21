package com.example.kidsmathsgame

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private val sessions: List<GameSession>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvOpEmoji: TextView = view.findViewById(R.id.tvOpEmoji)
        val tvScore: TextView = view.findViewById(R.id.tvScore)
        val tvAccuracy: TextView = view.findViewById(R.id.tvAccuracy)
        val tvDifficultyBadge: TextView = view.findViewById(R.id.tvDifficultyBadge)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val session = sessions[position]
        
        holder.tvOpEmoji.text = when (session.operation) {
            "+" -> "➕"
            "-" -> "➖"
            "*" -> "✖️"
            "/" -> "➗"
            else -> "❓"
        }
        
        holder.tvScore.text = session.score.toString()
        holder.tvAccuracy.text = "Accuracy: ${session.accuracy}%"
        holder.tvDate.text = session.playedAt
        
        holder.tvDifficultyBadge.text = session.difficulty
        val badgeColor = when (session.difficulty) {
            "EASY" -> "#4CAF50"
            "MEDIUM" -> "#FF9800"
            "HARD" -> "#F44336"
            else -> "#9e9ec8"
        }
        holder.tvDifficultyBadge.setBackgroundColor(Color.parseColor(badgeColor))
    }

    override fun getItemCount() = sessions.size
}
