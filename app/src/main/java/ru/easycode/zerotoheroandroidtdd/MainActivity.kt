package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (IS_REMOVED) {
            binding.rootLayout.removeView(binding.titleTextView)
        }

        binding.removeButton.setOnClickListener {
            IS_REMOVED = true
            binding.rootLayout.removeView(binding.titleTextView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(REMOVED_VIEW_KEY, IS_REMOVED)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        IS_REMOVED = savedInstanceState.getBoolean(REMOVED_VIEW_KEY, false)
    }

    companion object {
        private const val REMOVED_VIEW_KEY = "KEY"
        private var IS_REMOVED: Boolean = false
    }
}