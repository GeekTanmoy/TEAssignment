package com.example.techexactly.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techexactly.api.ApiResponse
import com.example.techexactly.api.Resource
import com.example.techexactly.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _appListFlow = MutableStateFlow<Resource<ApiResponse>?>(null)
    val appListFlow: StateFlow<Resource<ApiResponse>?>
        get() = _appListFlow

    fun appList(kidId:String) {
        viewModelScope.launch {
            _appListFlow.emit(Resource.LOADING)
        }
        viewModelScope.launch {
            try {
                val response = repository.appList(kidId)
                Log.e("App List", "Response : $response")
                Log.e("App List", "Response Body : ${response.body()}")
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _appListFlow.emit(Resource.SUCCESS(response.body()!!))
                    } else {
                        _appListFlow.emit(Resource.ERROR(response.body()!!.message))
                    }
                } else {
                    if (response.code() == 500) {
                        _appListFlow.emit(Resource.ERROR("We're unable to process your request, Please try again later."))
                    } else {
                        _appListFlow.emit(Resource.ERROR(response.message()))
                    }
                }
            } catch (e: java.lang.Exception) {
                _appListFlow.emit(Resource.ERROR("We're unable to process your request, Please try again later."))
            }
        }
    }
}