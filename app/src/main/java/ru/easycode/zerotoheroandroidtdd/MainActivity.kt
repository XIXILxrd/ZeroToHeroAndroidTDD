package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var state: State = State.Initialized()

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.removeButton.setOnClickListener {
            state = State.Clicked()
            state.apply(binding.rootLayout, binding.titleTextView, binding.removeButton)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable(KEY, state)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(KEY, State::class.java) as State
        } else {
            savedInstanceState.getSerializable(KEY) as State
        }

        state.apply(binding.rootLayout, binding.titleTextView, binding.removeButton)
    }

    companion object {
        private const val KEY = "KEY"
    }
}

interface State : Serializable {

    fun apply(linearLayout: LinearLayout, view: View, button: Button)

    class Initialized : State {

        override fun apply(linearLayout: LinearLayout, view: View, button: Button) = Unit
    }

    class Clicked : State {

        override fun apply(linearLayout: LinearLayout, view: View, button: Button) {
            linearLayout.removeView(view)
            button.isEnabled = false
        }
    }
}