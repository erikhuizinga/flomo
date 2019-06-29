package io.github.erikhuizinga.flomo.demo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.erikhuizinga.flomo.isNetworkConnectedFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class FlomoDemoViewModel(private val context: Context) : ViewModel() {
	val isConnectedData: LiveData<Boolean> get() = _isConnectedData
	private val _isConnectedData = MutableLiveData<Boolean>().apply { value = false }

	val counterData: LiveData<Int> get() = _counterData
	private val _counterData = MutableLiveData<Int>().apply { value = 0 }

	val isMonitoringNetwork: LiveData<Boolean> get() = _isMonitoringNetwork
	private val _isMonitoringNetwork = MutableLiveData<Boolean>()

	private val coroutineScope = CoroutineScope(Dispatchers.IO)
	private var currentJob: Job? = null

	private var counter = 0

	init {
		start()
	}

	override fun onCleared() {
		coroutineScope.cancel()
		super.onCleared()
	}

	fun start() {
		if (currentJob?.isActive == true) return // Do not restart already running job
		currentJob = coroutineScope.launch {
			context.isNetworkConnectedFlow.collect { isConnected ->
				Log.d(TAG, "Collected isConnected = $isConnected from flow")
				_counterData.postValue(++counter)
				_isConnectedData.postValue(isConnected)
			}
		}
		_isMonitoringNetwork.postValue(true)
	}

	fun stop() {
		currentJob?.cancel()
		counter = 0.also(_counterData::postValue)
		_isMonitoringNetwork.postValue(false)
	}

	@Suppress("UNCHECKED_CAST")
	class Factory(private val context: Context) : ViewModelProvider.Factory {
		override fun <T : ViewModel?> create(modelClass: Class<T>): T {
			return FlomoDemoViewModel(context.applicationContext) as T
		}
	}
}
