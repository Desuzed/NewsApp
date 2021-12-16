package com.desuzed.newsapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.desuzed.newsapp.App
import com.desuzed.newsapp.R
import com.desuzed.newsapp.databinding.ActivityMainBinding
import com.desuzed.newsapp.model.vm.NewsViewModel
import com.desuzed.newsapp.model.vm.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private val newsViewModel: NewsViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(App.instance.getRepo())
        )
            .get(NewsViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityBinding = ActivityMainBinding.inflate(
            layoutInflater
        )
        val view: View = activityBinding.root
        setContentView(view)
        //Проверка работоспособности
        newsViewModel.postValue("london")
        newsViewModel.observe(this, {
            Log.i("TAG", "onCreate: $it")
        })
    }
}