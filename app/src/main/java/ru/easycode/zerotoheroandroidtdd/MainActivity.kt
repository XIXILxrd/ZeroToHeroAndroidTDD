package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import kotlin.math.max

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var count: Count = Count.Base(step = 2, max = 4, min = 0)

    private lateinit var state: UiState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            state = count.initial(binding.countTextView.text.toString())
            state.apply(binding.countTextView, binding.incrementButton, binding.decrementButton)
        }

        binding.incrementButton.setOnClickListener {
            state = count.increment(binding.countTextView.text.toString())
            state.apply(binding.countTextView, binding.incrementButton, binding.decrementButton)
        }

        binding.decrementButton.setOnClickListener {
            state = count.decrement(binding.countTextView.text.toString())
            state.apply(binding.countTextView, binding.incrementButton, binding.decrementButton)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable(KEY, state)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(KEY, UiState::class.java) as UiState
        } else {
            savedInstanceState.getSerializable(KEY) as UiState
        }

        state.apply(binding.countTextView, binding.incrementButton, binding.decrementButton)
    }

    companion object {
        private const val KEY = "KEY"
    }
}