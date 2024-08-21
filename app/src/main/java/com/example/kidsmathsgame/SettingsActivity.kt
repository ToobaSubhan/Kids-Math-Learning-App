package com.example.kidsmathsgame

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val prefs = getSharedPreferences("kids_math_prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val switchSound = findViewById<SwitchCompat>(R.id.switchSound)
        val switchVibration = findViewById<SwitchCompat>(R.id.switchVibration)
        val switchStreak = findViewById<SwitchCompat>(R.id.switchStreak)
        val rgTimer = findViewById<RadioGroup>(R.id.rgTimer)
        val btnResetScores = findViewById<Button>(R.id.btnResetScores)

        // Load initial values
        switchSound.isChecked = prefs.getBoolean("pref_sound", true)
        switchVibration.isChecked = prefs.getBoolean("pref_vibration", true)
        switchStreak.isChecked = prefs.getBoolean("pref_streak", true)
        
        val timerVal = prefs.getString("pref_timer", "30")
        when (timerVal) {
            "30" -> rgTimer.check(R.id.rb30s)
            "60" -> rgTimer.check(R.id.rb60s)
            "0" -> rgTimer.check(R.id.rbInf)
        }

        // Listeners
        switchSound.setOnCheckedChangeListener { _, isChecked ->
            editor.putBoolean("pref_sound", isChecked).apply()
        }
        switchVibration.setOnCheckedChangeListener { _, isChecked ->
            editor.putBoolean("pref_vibration", isChecked).apply()
        }
        switchStreak.setOnCheckedChangeListener { _, isChecked ->
            editor.putBoolean("pref_streak", isChecked).apply()
        }
        rgTimer.setOnCheckedChangeListener { _, checkedId ->
            val value = when (checkedId) {
                R.id.rb30s -> "30"
                R.id.rb60s -> "60"
                R.id.rbInf -> "0"
                else -> "30"
            }
            editor.putString("pref_timer", value).apply()
        }

        btnResetScores.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Reset All Scores")
                .setMessage("Are you sure you want to delete all your high scores? This cannot be undone.")
                .setPositiveButton("Reset") { _, _ ->
                    resetScores()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun resetScores() {
        // Placeholder for SQLite reset logic (TASK S1)
        // If DatabaseHelper exists, call its reset method here.
        val dbHelper = DatabaseHelper(this)
        dbHelper.resetAllScores()
    }
}
