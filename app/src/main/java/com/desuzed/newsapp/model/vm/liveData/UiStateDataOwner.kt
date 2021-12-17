package com.desuzed.newsapp.model.vm.liveData

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.io.IOException

class UiStateDataOwner : LiveDataOwner <UiState> {
    private val uiLiveData = MutableLiveData <UiState>()
    override fun postValue(t: UiState) {
        uiLiveData.postValue(t)
    }

    override fun showValue(): UiState? = uiLiveData.value

    override fun observe(owner: LifecycleOwner, observer: Observer<UiState>) {
        uiLiveData.observe(owner, observer)
    }

    override fun removeObserver(observer: Observer<UiState>) {
        uiLiveData.removeObserver(observer)
    }
}

sealed class UiState {
    class Success : UiState()
    class Loading : UiState()
    class Error : UiState()
    class NoData : UiState()
}