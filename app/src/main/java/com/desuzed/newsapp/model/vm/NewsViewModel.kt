package com.desuzed.newsapp.model.vm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desuzed.newsapp.data.repository.RepoApp
import com.desuzed.newsapp.model.Error
import com.desuzed.newsapp.model.News
import com.desuzed.newsapp.model.vm.liveData.ErrorMessageDataOwner
import com.desuzed.newsapp.model.vm.liveData.NewsDataOwner
import com.desuzed.newsapp.model.vm.liveData.QueryDataOwner
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsViewModel(private val repoApp: RepoApp) : ViewModel(), INewsViewModel {
    //TODO injection
    private val newsDataOwner = NewsDataOwner()
    private val errorMessageDataOwner = ErrorMessageDataOwner()
    private val queryDataOwner = QueryDataOwner()


    override fun fetchDataFromApi(query: String) =
        viewModelScope.launch {
            val result: Pair<News?, Error?> = repoApp.fetchNewsFromApi(query)
            result.first?.let { newsDataOwner.postValue(it) }
            handleError(result.second)
        }

    override fun observeNews(owner: LifecycleOwner, observer: Observer<News>) {
        newsDataOwner.observe(owner, observer)
    }

    override fun observeError(owner: LifecycleOwner, observer: Observer<String>) {
        errorMessageDataOwner.observe(owner, observer)
    }

    override fun observeQuery(owner: LifecycleOwner, observer: Observer<String>) {
        queryDataOwner.observe(owner, observer)
    }


    override fun showQuery(): String? = queryDataOwner.showValue()


    override fun showNews(): News? = newsDataOwner.showValue()


    override fun postQuery(query: String) {
        queryDataOwner.postValue(query)
    }

    override fun removeNewsObserver(observer: Observer<News>) {
        newsDataOwner.removeObserver(observer)
    }

    override fun removeErrorObserver(observer: Observer<String>) {
        errorMessageDataOwner.removeObserver(observer)
    }

    override fun removeQueryObserver(observer: Observer<String>) {
        queryDataOwner.removeObserver(observer)
    }

    private fun handleError(error: Error?) {
        if (error == null) {
            return
        }
        errorMessageDataOwner.postValue(error.message)
    }
}

interface INewsViewModel {
    fun fetchDataFromApi(query: String): Job
    fun showNews(): News?
    fun showQuery(): String?
    fun postQuery(query: String)
    fun observeNews(owner: LifecycleOwner, observer: Observer<News>)
    fun observeError(owner: LifecycleOwner, observer: Observer<String>)
    fun observeQuery(owner: LifecycleOwner, observer: Observer<String>)
    fun removeNewsObserver(observer: Observer<News>)
    fun removeErrorObserver(observer: Observer<String>)
    fun removeQueryObserver(observer: Observer<String>)
}