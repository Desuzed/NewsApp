package com.desuzed.newsapp

import android.app.Application
import com.desuzed.newsapp.data.ErrorProviderImpl
import com.desuzed.newsapp.data.repository.LocalDataSourceImpl
import com.desuzed.newsapp.data.repository.RemoteDataSourceImpl
import com.desuzed.newsapp.data.repository.RepoApp
import com.desuzed.newsapp.data.repository.RepoAppImpl
import com.desuzed.newsapp.data.room.RoomDbApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class App : Application() {
    private val roomDbApp by lazy { RoomDbApp.getDatabase(this) }
    private val errorProvider by lazy { ErrorProviderImpl(resources) }
    private val localDataSource by lazy { LocalDataSourceImpl(errorProvider, roomDbApp.articleDao()) }
    private val remoteDataSource by lazy { RemoteDataSourceImpl() }
    private val repositoryApp by lazy { RepoAppImpl(localDataSource, remoteDataSource) }

    fun getRepo(): RepoApp  = repositoryApp

    override fun onCreate() {
        super.onCreate()
        instance = this

    }
    companion object {
        lateinit var instance: App
            private set
    }
}