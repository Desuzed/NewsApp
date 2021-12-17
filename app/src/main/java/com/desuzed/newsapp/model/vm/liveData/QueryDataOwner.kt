package com.desuzed.newsapp.model.vm.liveData

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class QueryDataOwner : LiveDataOwner<String> {
    private val queryLiveData = MutableLiveData<String>()

    override fun postValue(t: String) {
        queryLiveData.postValue(t)
    }

    override fun showValue(): String? = queryLiveData.value



    override fun observe(owner: LifecycleOwner, observer: Observer<String>) {
        queryLiveData.observe(owner, observer)
    }

    override fun removeObserver(observer: Observer<String>) {
        queryLiveData.removeObserver(observer)
    }
}