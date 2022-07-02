package com.mobulous.helper

sealed class NetworkReponse<T> {
    class Success<T>(val data: T? = null) : NetworkReponse<T>()
    class Error<T>(val errorMessage: String) : NetworkReponse<T>()
    object Loading: NetworkReponse<Nothing>()

}