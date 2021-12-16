package com.desuzed.newsapp.model.vm

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desuzed.everyweather.data.network.retrofit.NetworkResponse
import com.desuzed.newsapp.data.repository.RepoApp
import com.desuzed.newsapp.model.News
import kotlinx.coroutines.launch

class NewsViewModel(private val repoApp: RepoApp) : ViewModel(), INewsViewModel {
    //TODO injection
    private val newsDataOwner = NewsDataOwner()
    override fun showValue(): News? {
        return newsDataOwner.showValue()
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<News>) {
        newsDataOwner.observe(owner, observer)
    }

    fun postValue(query: String) =
        viewModelScope.launch {
            val result = repoApp.getNews(query)
            when (result) {
                is NetworkResponse.Success -> newsDataOwner.postValue(result.body)
                is NetworkResponse.ApiError -> Log.i("TAG", "postValue: ")
                is NetworkResponse.NetworkError -> Log.i("TAG", "postValue: ")
                is NetworkResponse.UnknownError -> Log.i("TAG", "postValue: ")
            }
        }


}

interface INewsViewModel : LiveDataOwner<News>