package com.desuzed.newsapp.model.vm

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
    //TODO non public
    fun postValue(news: News) {
        newsLiveData.postValue(news)
    }

}

interface LiveDataOwner<T> {
    fun showValue(): T?
    fun observe(owner: LifecycleOwner, observer: Observer<T>)
}