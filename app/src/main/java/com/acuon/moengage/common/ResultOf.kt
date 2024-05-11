package com.acuon.moengage.common

sealed class ResultOf<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ResultOf<T>(data)
    class Error<T>(message: String, data: T? = null) : ResultOf<T>(data, message)
    class Loading<T>(data: T? = null) : ResultOf<T>(data)
}