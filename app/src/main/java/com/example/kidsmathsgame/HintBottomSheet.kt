package com.example.kidsmathsgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HintBottomSheet : BottomSheetDialogFragment() {

    private var onDismissListener: (() -> Unit)? = null

    companion object {
        fun newInstance(question: String, hint: String, answer: Int): HintBottomSheet {
            val fragment = HintBottomSheet()
            val args = Bundle().apply {
                putString("question", question)
                putString("hint", hint)
                putInt("answer", answer)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hint_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val question = arguments?.getString("question") ?: ""
        val hint = arguments?.getString("hint") ?: ""
        val answer = arguments?.getInt("answer") ?: 0

        view.findViewById<TextView>(R.id.tvHintQuestion).text = question
        view.findViewById<TextView>(R.id.tvHintText).text = hint
        view.findViewById<TextView>(R.id.tvCorrectAnswer).text = "Correct Answer: $answer"

        view.findViewById<Button>(R.id.btnNext).setOnClickListener {
            onDismissListener?.invoke()
            dismiss()
        }
    }

    fun setOnDismissListener(listener: () -> Unit) {
        onDismissListener = listener
    }

    override fun getTheme(): Int = R.style.CustomBottomSheetDialogTheme
}
