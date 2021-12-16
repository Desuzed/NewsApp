package com.desuzed.newsapp.data.repository

import com.desuzed.newsapp.data.ErrorProvider

class LocalDataSourceImpl (private val errorProvider : ErrorProvider) : LocalDataSource {
    override fun parseCode(code: Any): String {
        return errorProvider.parseCode(code)
    }
}

interface LocalDataSource : ErrorProvider {
}