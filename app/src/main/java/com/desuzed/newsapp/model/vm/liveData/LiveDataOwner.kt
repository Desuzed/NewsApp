package com.desuzed.newsapp.model.vm.liveData

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.desuzed.newsapp.model.News

class NewsDataOwner : LiveDataOwner<News> {
    private val newsLiveData = MutableLiveData<News>()
    override fun observe(owner: LifecycleOwner, observer: Observer<News>) {
        newsLiveData.observe(owner, observer)
    }
    override fun showValue(): News? = newsLiveData.value

    override fun postValue(news: News) {
        newsLiveData.postValue(news)
    }

    override fun removeObserver(observer: Observer<News>) {
        newsLiveData.removeObserver(observer)
    }

}

interface LiveDataOwner<T> {
    fun postValue(t : T)
    fun showValue(): T?
    fun observe(owner: LifecycleOwner, observer: Observer<T>)
    fun removeObserver(observer: Observer<T>)
}