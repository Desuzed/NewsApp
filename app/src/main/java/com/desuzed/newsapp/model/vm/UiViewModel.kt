package com.desuzed.newsapp.model.vm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.desuzed.newsapp.model.vm.liveData.LiveDataOwner
import com.desuzed.newsapp.model.vm.liveData.UiState
import com.desuzed.newsapp.model.vm.liveData.UiStateDataOwner

class UiViewModel : ViewModel(), LiveDataOwner<UiState>{
    private val uiStateDataOwner = UiStateDataOwner()
    override fun postValue(t: UiState) {
        uiStateDataOwner.postValue(t)
    }

    override fun showValue(): UiState? =
        uiStateDataOwner.showValue()


    override fun observe(owner: LifecycleOwner, observer: Observer<UiState>) {
        uiStateDataOwner.observe(owner, observer)
    }

    override fun removeObserver(observer: Observer<UiState>) {
        uiStateDataOwner.removeObserver(observer)
    }


}