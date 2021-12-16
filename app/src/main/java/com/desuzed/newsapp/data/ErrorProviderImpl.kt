package com.desuzed.newsapp.data

import android.content.res.Resources
import com.desuzed.newsapp.R
import com.desuzed.newsapp.data.ErrorProvider.Companion.API_KEY_DISABLED
import com.desuzed.newsapp.data.ErrorProvider.Companion.API_KEY_EXHAUSTED
import com.desuzed.newsapp.data.ErrorProvider.Companion.API_KEY_INVALID
import com.desuzed.newsapp.data.ErrorProvider.Companion.API_KEY_MISSING
import com.desuzed.newsapp.data.ErrorProvider.Companion.PARAMETER_INVALID
import com.desuzed.newsapp.data.ErrorProvider.Companion.PARAMETER_MISSING
import com.desuzed.newsapp.data.ErrorProvider.Companion.RATE_LIMITED
import com.desuzed.newsapp.data.ErrorProvider.Companion.SOURCES_TOO_MANY
import com.desuzed.newsapp.data.ErrorProvider.Companion.SOURCE_DOES_NOT_EXIST

class ErrorProviderImpl(private val resources: Resources) : ErrorProvider {
    override fun parseCode(code: Any): String {
        return when (code) {
            11 -> resources.getString(R.string.check_internet_connection)
            10 -> resources.getString(R.string.nothing_found)
            30 -> resources.getString(R.string.unknown_app_error)
            in 400..401 -> resources.getString(R.string.api_key_error)
            API_KEY_DISABLED -> resources.getString(R.string.api_key_error)
            API_KEY_EXHAUSTED -> resources.getString(R.string.api_key_error)
            API_KEY_INVALID -> resources.getString(R.string.api_key_error)
            API_KEY_MISSING -> resources.getString(R.string.api_key_error)
            RATE_LIMITED -> resources.getString(R.string.api_key_error)
            PARAMETER_INVALID -> resources.getString(R.string.parameter_invalid)
            PARAMETER_MISSING -> resources.getString(R.string.parameter_missing)
            SOURCES_TOO_MANY -> resources.getString(R.string.too_many_source)
            SOURCE_DOES_NOT_EXIST -> resources.getString(R.string.source_does_not_exist)
            else -> resources.getString(R.string.internal_app_error)
        }
    }
}

interface ErrorProvider {
    fun parseCode(code: Any): String
//TODO Не самое лучшее апи в плане обработки ошибок
    companion object {
        const val NO_DATA = 10
        const val NO_INTERNET = 11
        const val UNKNOWN = 12
        const val FAIL = -1
        const val API_KEY_DISABLED = "apiKeyDisabled"
        const val API_KEY_EXHAUSTED = "apiKeyExhausted"
        const val API_KEY_INVALID = "apiKeyInvalid"
        const val API_KEY_MISSING = "apiKeyMissing"
        const val PARAMETER_INVALID = "parameterInvalid"
        const val PARAMETER_MISSING = "parametersMissing"
        const val RATE_LIMITED = "rateLimited"
        const val SOURCES_TOO_MANY = "sourcesTooMany"
        const val SOURCE_DOES_NOT_EXIST = "sourceDoesNotExist"
    }
}

