package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainViewModel(
    private val liveDataWrapper: LiveDataWrapper,
    private val repository: Repository
) : ViewModel(), ProvideLiveData {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun load() {
        liveDataWrapper.update(UiState.ShowProgress)

        viewModelScope.launch {
            repository.load()
            liveDataWrapper.update(UiState.ShowData)
        }
    }

    override fun liveData(): LiveData<UiState> = liveDataWrapper.liveData()

    companion object {
        val REPOSITORY_KEY = object : CreationExtras.Key<Repository> {}
        val LIVE_DATA_WRAPPER = object : CreationExtras.Key<LiveDataWrapper> {}

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val liveDataWrapper = this[LIVE_DATA_WRAPPER] as LiveDataWrapper
                val repository = this[REPOSITORY_KEY] as Repository

                MainViewModel(
                    liveDataWrapper = liveDataWrapper,
                    repository = repository
                )
            }
        }
    }
}