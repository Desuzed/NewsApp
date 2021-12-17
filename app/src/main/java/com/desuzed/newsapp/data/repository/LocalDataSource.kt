package com.desuzed.newsapp.data.repository

import com.desuzed.newsapp.data.ErrorProvider
import com.desuzed.newsapp.data.room.ArticleDAO
import com.desuzed.newsapp.data.room.ArticleDTO
import kotlinx.coroutines.*

class LocalDataSourceImpl (private val errorProvider : ErrorProvider, private val articleDAO: ArticleDAO) : LocalDataSource {
    private var job : Job? = null
    override fun insertArticles(list: List<ArticleDTO>)   {
        job =  CoroutineScope(Dispatchers.IO).launch{
            articleDAO.insertArticles(list)
        }
    }

    override fun deleteAllArticles()  {
        job = CoroutineScope(Dispatchers.IO).launch{
            articleDAO.deleteAll()
        }
    }

    override suspend fun getCachedArticles(): List<ArticleDTO>  = withContext(Dispatchers.IO){
        articleDAO.getCachedArticles()
    }

    override fun clearJob() {
        job = null
    }


    override fun parseCode(code: Any): String {
        return errorProvider.parseCode(code)
    }
}

interface LocalDataSource : ErrorProvider, RoomProvider{
    fun clearJob ()
}

interface RoomProvider{
    fun insertArticles (list : List <ArticleDTO>)
    fun deleteAllArticles ()
    suspend fun getCachedArticles (): List <ArticleDTO>
}