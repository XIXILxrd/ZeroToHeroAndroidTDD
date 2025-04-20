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
    private val repository: Repository,
) : ViewModel() {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun liveData(): LiveData<UiState> = liveDataWrapper.liveData()

    fun load() {
        liveDataWrapper.update(UiState.ShowProgress)

        viewModelScope.launch {
            repository.load()
            liveDataWrapper.update(UiState.ShowData)
        }
    }

    fun save(bundleWrapper: BundleWrapper.Save) {
        liveDataWrapper.save(bundleWrapper)
    }

    fun restore(bundleWrapper: BundleWrapper.Restore) {
       liveDataWrapper.update(bundleWrapper.restore())
    }

    companion object {
        val LIVE_DATA_WRAPPER_KEY = object : CreationExtras.Key<LiveDataWrapper> {}
        val REPOSITORY_KEY = object : CreationExtras.Key<Repository> {}

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val liveDataWrapper = this[LIVE_DATA_WRAPPER_KEY] as LiveDataWrapper
                val repository = this[REPOSITORY_KEY] as Repository

                MainViewModel(liveDataWrapper, repository)
            }
        }
    }
}