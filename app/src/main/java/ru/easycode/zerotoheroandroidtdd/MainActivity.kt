package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titleTextView.text = savedInstanceState?.getString(TITLE_KEY) ?: getString(R.string.hello_world)

        binding.changeButton.setOnClickListener {
            binding.titleTextView.text = getString(R.string.i_am_android_developer)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(TITLE_KEY, binding.titleTextView.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        binding.titleTextView.text = savedInstanceState.getString(TITLE_KEY)
    }

    companion object {
        private const val TITLE_KEY = "TITLE_KEY"
    }
}
