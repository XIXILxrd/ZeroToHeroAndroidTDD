package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var state: State = State.Initialize()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.removeButton.setOnClickListener {
            state = State.RemoveButtonPressed()
            state.apply(binding.rootLayout, binding.titleTextView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable(REMOVED_VIEW_KEY, state)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            state = savedInstanceState.getSerializable(REMOVED_VIEW_KEY, State::class.java) as State
        } else {
            state = savedInstanceState.getSerializable(REMOVED_VIEW_KEY) as State
        }
        state.apply(binding.rootLayout, binding.titleTextView)
    }

    companion object {
        private const val REMOVED_VIEW_KEY = "KEY"
    }
}

interface State : Serializable {

    fun apply(linearLayout: LinearLayout, view: View)

    class Initialize() : State {

        override fun apply(linearLayout: LinearLayout, view: View) = Unit
    }

    class RemoveButtonPressed() : State {

        override fun apply(linearLayout: LinearLayout, view: View) {
            linearLayout.removeView(view)
        }
    }
}