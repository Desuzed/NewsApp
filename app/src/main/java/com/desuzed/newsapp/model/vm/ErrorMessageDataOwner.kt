package com.desuzed.newsapp.model.vm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class ErrorMessageDataOwner : LiveDataOwner<String> {
    private val errorMessageLiveData = MutableLiveData<String>()
    override fun observe(owner: LifecycleOwner, observer: Observer<String>) {
        errorMessageLiveData.observe(owner, observer)
    }

    override fun showValue(): String? = errorMessageLiveData.value
    override fun postValue(t : String) {
        errorMessageLiveData.postValue(t)
    }

    override fun removeObserver(observer: Observer<String>) {
        errorMessageLiveData.removeObserver(observer)
    }

}