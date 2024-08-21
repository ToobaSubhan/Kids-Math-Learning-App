package com.example.kidsmathsgame

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WeaknessReportActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weakness_report)

        db = DatabaseHelper(this)
        
        setupSection(R.id.rvWeakAddition, "+")
        setupSection(R.id.rvWeakSubtraction, "-")
        setupSection(R.id.rvWeakMultiplication, "*")
        setupSection(R.id.rvWeakDivision, "/")

        findViewById<Button>(R.id.btnBack).setOnClickListener { finish() }
    }

    private fun setupSection(rvId: Int, op: String) {
        val rv = findViewById<RecyclerView>(rvId)
        val tvEmpty = findViewById<TextView>(resources.getIdentifier("tvEmpty$op", "id", packageName))
        
        val weakQuestions = db.getTopWeakQuestions(op)
        
        if (weakQuestions.isEmpty()) {
            rv.visibility = View.GONE
            tvEmpty?.visibility = View.VISIBLE
        } else {
            rv.visibility = View.VISIBLE
            tvEmpty?.visibility = View.GONE
            rv.layoutManager = LinearLayoutManager(this)
            rv.adapter = WeaknessAdapter(weakQuestions) { question ->
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("cals", question.operation)
                intent.putExtra("DIFFICULTY", "MEDIUM")
                startActivity(intent)
            }
        }
    }

    class WeaknessAdapter(
        private val items: List<WeakQuestion>,
        private val onPractice: (WeakQuestion) -> Unit
    ) : RecyclerView.Adapter<WeaknessAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvLabel: TextView = view.findViewById(R.id.tvQuestionLabel)
            val pbWeakness: ProgressBar = view.findViewById(R.id.pbWeakness)
            val tvStats: TextView = view.findViewById(R.id.tvQuestionStats)
            val btnPractice: Button = view.findViewById(R.id.btnPractice)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weak_question, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            val opSymbol = when(item.operation) {
                "+" -> "+"
                "-" -> "−"
                "*" -> "×"
                "/" -> "÷"
                else -> item.operation
            }
            holder.tvLabel.text = "${item.num1} $opSymbol ${item.num2}"
            holder.pbWeakness.progress = (item.score * 100).toInt()
            holder.tvStats.text = "Correct: ${item.correct} | Wrong: ${item.wrong}"
            holder.btnPractice.setOnClickListener { onPractice(item) }
        }

        override fun getItemCount() = items.size
    }
}
