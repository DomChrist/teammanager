package de.dom.cishome.myapplication.config

data class AsyncResponse<T>(val onSuccess: (t: T) -> Unit, val onError: (e: Exception) -> Unit )