package com.desuzed.newsapp.model.vm

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desuzed.newsapp.data.repository.RepoApp
import com.desuzed.newsapp.model.Error
import com.desuzed.newsapp.model.News
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsViewModel(private val repoApp: RepoApp) : ViewModel(), INewsViewModel {
    //TODO injection
    private val newsDataOwner = NewsDataOwner()
    private val errorMessageDataOwner = ErrorMessageDataOwner()
    override fun showNews(): News? {
        return newsDataOwner.showValue()
    }

    override fun observeNews(owner: LifecycleOwner, observer: Observer<News>) {
        newsDataOwner.observe(owner, observer)
    }


    override fun fetchDataFromApi(query: String) =
        viewModelScope.launch {
            val result: Pair<News?, Error?> = repoApp.fetchNewsFromApi(query)
            result.first?.let { newsDataOwner.postValue(it) }
            handleError(result.second)


        }

    override fun showError(): String? {
        return errorMessageDataOwner.showValue()
    }

    override fun observeError(owner: LifecycleOwner, observer: Observer<String>) {
        errorMessageDataOwner.observe(owner, observer)
    }

    override fun removeNewsObserver(observer: Observer<News>) {
        newsDataOwner.removeObserver(observer)
    }

    override fun removeErrorObserver(observer: Observer<String>) {
        errorMessageDataOwner.removeObserver(observer)
    }

    private fun handleError(error: Error?) {
        Log.i("TAG", "handleError: ${error?.message}")
        if (error == null) {
            return
        }
        errorMessageDataOwner.postValue(error.message)
    }
}

interface INewsViewModel {
    fun fetchDataFromApi(query: String): Job
    fun showNews(): News?
    fun showError(): String?
    fun observeNews(owner: LifecycleOwner, observer: Observer<News>)
    fun observeError(owner: LifecycleOwner, observer: Observer<String>)
    fun removeNewsObserver(observer: Observer<News>)
    fun removeErrorObserver(observer: Observer<String>)
}