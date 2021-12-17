package com.desuzed.newsapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.desuzed.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
//    private val newsViewModel: NewsViewModel by lazy {
//        ViewModelProvider(
//            this,
//            ViewModelFactory(App.instance.getRepo())
//        )
//            .get(NewsViewModel::class.java)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityBinding = ActivityMainBinding.inflate(
            layoutInflater
        )
        val view: View = activityBinding.root
        setContentView(view)
    }
}