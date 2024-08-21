package com.example.kidsmathsgame

object HintEngine {
    fun generateHint(operation: String, n1: Int, n2: Int): String {
        return when (operation) {
            "+" -> generateAdditionHint(n1, n2)
            "-" -> generateSubtractionHint(n1, n2)
            "*" -> generateMultiplicationHint(n1, n2)
            "/" -> generateDivisionHint(n1, n2)
            else -> "Try your best!"
        }
    }

    private fun generateAdditionHint(n1: Int, n2: Int): String {
        if (n2 <= 5) {
            val sb = StringBuilder("$n1")
            var current = n1
            for (i in 1..n2) {
                current++
                sb.append(" → $current")
            }
            return "Count up: $sb"
        } else {
            val split = (n2 / 10) * 10
            val rem = n2 % 10
            return if (split > 0) {
                "Break it down:\n$n1 + $split = ${n1 + split}\n${n1 + split} + $rem = ${n1 + n2}"
            } else {
                "Think: $n1 + $n2"
            }
        }
    }

    private fun generateSubtractionHint(n1: Int, n2: Int): String {
        if (n2 <= 5) {
            val sb = StringBuilder("$n1")
            var current = n1
            for (i in 1..n2) {
                current--
                sb.append(" → $current")
            }
            return "Count down: $sb"
        }
        return "Think: What plus $n2 equals $n1?"
    }

    private fun generateMultiplicationHint(n1: Int, n2: Int): String {
        when {
            n2 == 2 -> return "Double it: $n1 + $n1 = ${n1 * 2}"
            n2 == 5 -> return "Count by 5s: " + (1..5).joinToString(", ") { (it * n1).toString() }
            n2 == 10 -> return "Just add a zero: $n1 → ${n1}0"
            n1 <= 5 && n2 <= 5 -> {
                val list = List(n2) { n1.toString() }
                return "Repeated addition:\n" + list.joinToString(" + ") + " = ${n1 * n2}"
            }
            else -> {
                val split = 10
                if (n1 > 10) {
                    return "Break $n1 into 10 and ${n1-10}:\n10 × $n2 = ${10*n2}\n${n1-10} × $n2 = ${(n1-10)*n2}\nAdd them up!"
                }
                return "Multiplication is just fast adding!"
            }
        }
    }

    private fun generateDivisionHint(n1: Int, n2: Int): String {
        val target = n1 / n2
        val sb = StringBuilder()
        for (i in 1..minOf(target, 5)) {
            sb.append("${i * n2} ")
        }
        return "Think: What × $n2 = $n1?\nCount by ${n2}s: $sb..."
    }
}
