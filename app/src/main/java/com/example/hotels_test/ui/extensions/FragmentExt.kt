package com.example.hotels_test.ui.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun <T> Fragment.observeAsEvent(flow: Flow<T>, onEvent: (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }
}

fun <T> Fragment.observe(flow: Flow<T>, onStateChanged: (T) -> Unit) {
    lifecycleScope.launch(Dispatchers.IO) {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main) {
                flow.collect(onStateChanged)
            }
        }
    }
}