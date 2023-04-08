package com.example.submission2.Main

import android.util.Log
import androidx.lifecycle.*
import com.example.submission2.Database.API.ApiConfig
import com.example.submission2.Response.ItemsItem
import com.example.submission2.Response.UserResponse
import com.example.submission2.Theme.ThemePreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: ThemePreferences): ViewModel() {
    private val _searchUsers = MutableLiveData<List<ItemsItem>>()
    val searchUsers: LiveData<List<ItemsItem>> = _searchUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    companion object{
        const val TAG = "MainViewModel"
        private const val USERNAME = "username"
    }

    init {
        getUsers(USERNAME)
    }

    fun getUsers(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _searchUsers.value = response.body()?.items
                    Log.d(TAG, "onResponse: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}