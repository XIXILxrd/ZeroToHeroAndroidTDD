package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.MutableCreationExtras
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            store = this.viewModelStore,
            factory = MainViewModel.Factory,
            defaultCreationExtras = MutableCreationExtras().apply {
                set(MainViewModel.LIVE_DATA_WRAPPER, LiveDataWrapper.Base())
                set(MainViewModel.REPOSITORY_KEY, Repository.Base())
            }
        )[MainViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.actionButton.setOnClickListener {
            viewModel.load()
        }

        viewModel.liveData().observe(this) { state ->
            state.apply(binding.progressBar, binding.titleTextView, binding.actionButton)
        }
    }
}