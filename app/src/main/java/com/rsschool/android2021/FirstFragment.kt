package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null

    private var minEditText: EditText? = null
    private var maxEditText: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        generateButton?.isEnabled = false

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        minEditText = view.findViewById(R.id.min_value)
        maxEditText = view.findViewById(R.id.max_value)

        generateButton?.setOnClickListener {
            val min = minEditText?.text.toString().toInt()
            val max = maxEditText?.text.toString().toInt()

            if (min > max) {
                Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show()
            } else if (min < 0 || max < 0) {
                Toast.makeText(activity, R.string.negative_error, Toast.LENGTH_LONG).show()
            } else {
                    (activity as MainActivity).transferToSecondFragment(min, max)
            }
        }

        minEditText?.doOnTextChanged { text, start, count, after ->
            generateButton?.isEnabled = !text.isNullOrEmpty() && !maxEditText?.text.isNullOrEmpty()
        }

        maxEditText?.doOnTextChanged { text, start, count, after ->
            generateButton?.isEnabled = !text.isNullOrEmpty() && !minEditText?.text.isNullOrEmpty()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}