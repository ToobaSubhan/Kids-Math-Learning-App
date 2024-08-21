# Technical Documentation: Enjoy Maths App

## 1. Project Overview
"Enjoy Maths" is a gamified educational Android application built with 100% Kotlin. It uses an adaptive learning engine to help children master basic arithmetic operations (+, -, *, /) through reinforcement and educational scaffolding.

## 2. Tech Stack
- **Language:** Kotlin
- **Database:** SQLite (Local Persistence)
- **UI:** XML Layouts + Material Design 3
- **Audio:** ToneGenerator API (Dynamic Audio Feedback)
- **Graphics:** Custom Canvas Views + Property Animators
- **Persistence:** SQLite + SharedPreferences

## 3. Architecture Overview
The app follows an **Activity-based Modular Architecture**. It separates the UI (Activities) from the business logic (Generators/Engines) and data (SQLite).

### Core Components:
- **Presentation:** Activities & Fragment BottomSheets.
- **Logic:** `AdaptiveQuestionGenerator`, `HintEngine`.
- **Data:** `DatabaseHelper` (SQLite OpenHelper).
- **Graphics:** `StarBackgroundView` (Custom View).

---

## 4. Activity-Wise Functions

### PlayActivity (Launcher & Hub)
- `startOwlAnimation()`: Uses `ObjectAnimator` for the floating mascot effect.
- `updateDailyCard()`: Fetches real-time streak and challenge status from the DB.
- **Navigation:** Routes users to difficulty selection, settings, or performance reports.

### MainActivity (The Game Engine)
- `NextQuestion()`: Uses the `AdaptiveQuestionGenerator` to decide which question to show based on the 70/30 probability rule.
- `optionSelect()`: Central point for input handling and triggering the recording of answer stats.
- `showHint()`: Pauses the game and launches `HintBottomSheet` with educational logic from `HintEngine`.
- `startTimer()`: Manages game duration and triggers "Time Up" logic.

### DailyChallengeActivity
- `loadTodayChallenge()`: Dynamically assigns operations based on the current day of the week.
- `setupCalendar()`: Renders a 30-day performance grid using database records.

### WeaknessReportActivity
- `setupSection()`: Aggregates stats from `question_stats` table to show top 3 mistakes per operation.
- **Practice Mode:** Launches `MainActivity` with a specific focus on identified weak questions.

### GameOverActivity
- `onGameOver()`: Calculates final accuracy and saves the session to SQLite.
- `updateHighScore()`: Compares current score with the record and updates if a new best is achieved.

---

## 5. Intelligent Helpers

### AdaptiveQuestionGenerator.kt
- **Reinforcement Logic:** If the database has ≥10 entries, it has a 70% chance to fetch questions from the `question_stats` table where `wrong_count > correct_count`.

### HintEngine.kt
- **Scaffolding Logic:** Generates human-readable hints:
  - **Addition:** Count-up chains (3 -> 4 -> 5).
  - **Subtraction:** "What plus X equals Y?" logic.
  - **Multiplication:** Repeated addition or "break-it-down" rules (e.g., split 12 into 10 and 2).

### DatabaseHelper.kt (SQLite Schema)
- `high_scores`: Best scores per category.
- `game_sessions`: History for Recent Games.
- `question_stats`: The "Memory" of the app (stores corrected/wrong counts per number pair).
- `daily_challenge`: Tracks completion dates and streaks.

---

## 6. Feedback & Interactivity
- **Haptic Feedback:** `Vibrator` class used for unique vibration patterns on errors vs. success.
- **Audio Feedback:** `ToneGenerator` used for low-latency beeps and alerts.
- **Visuals:** `StarBackgroundView` uses a separate thread-like drawing loop via `invalidate()` to animate 50+ stars independently.
