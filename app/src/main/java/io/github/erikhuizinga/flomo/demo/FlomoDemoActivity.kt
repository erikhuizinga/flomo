package io.github.erikhuizinga.flomo.demo

import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.github.erikhuizinga.flomo.R
import kotlinx.android.synthetic.main.activity_flomo_demo.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class FlomoDemoActivity : AppCompatActivity() {
	private val flomoDemoViewModel by lazy {
		ViewModelProviders.of(
			this,
			FlomoDemoViewModel.Factory(applicationContext)
		)[FlomoDemoViewModel::class.java]
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_flomo_demo)

		flomoDemoViewModel.isConnectedData.observe(this, Observer(::updateConnectivityUI))
		flomoDemoViewModel.isMonitoringNetwork.observe(this, Observer(::updateMonitoringUI))
		flomoDemoViewModel.counterData.observe(this, Observer(::updateCounter))
	}

	private fun updateConnectivityUI(isConnected: Boolean) {
		Log.d(TAG, "Updating connectivity UI")

		@StringRes val stringResId: Int
		@DrawableRes val drawableResId: Int
		if (isConnected) {
			stringResId = R.string.network_connected
			drawableResId = R.drawable.ic_check_circle_black_24dp
		} else {
			stringResId = R.string.network_not_connected
			drawableResId = R.drawable.ic_error_black_24dp
		}

		networkConnectionText.apply {
			setText(stringResId)
			setCompoundDrawablesWithIntrinsicBounds(
				0, drawableResId, 0, 0
			)
		}
	}

	private fun updateMonitoringUI(isMonitoring: Boolean) {
		Log.d(TAG, "Updating monitoring UI")

		if (isMonitoring) {
			button.apply {
				setText(R.string.stop)
				setOnClickListener { flomoDemoViewModel.stop() }
			}
		} else {
			button.apply {
				setText(R.string.start)
				setOnClickListener { flomoDemoViewModel.start() }
			}
			networkConnectionText.apply {
				setCompoundDrawablesWithIntrinsicBounds(
					0,
					R.drawable.ic_pause_circle_filled_black_24dp,
					0,
					0
				)
				setText(R.string.network_not_monitoring)
			}
		}
	}

	private fun updateCounter(count: Int) {
		Log.d(TAG, "Updating counter text")
		counter.text = getString(R.string.counter, count.toString())
	}
}
