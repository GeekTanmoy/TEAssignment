package com.example.techexactly.api

sealed class Resource<out R> {
    data class SUCCESS<out R>(val data: R) : Resource<R>()
    data class ERROR<out R>(val message: String,val code:Int=0) : Resource<R>()
    data object LOADING:Resource<Nothing>()
}