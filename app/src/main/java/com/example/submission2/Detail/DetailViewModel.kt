package com.example.submission2.Detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.submission2.Database.API.ApiConfig
import com.example.submission2.Database.Favorite
import com.example.submission2.Database.FavoriteDao
import com.example.submission2.Database.FavoriteDatabase
import com.example.submission2.Repository.FavRepository
import com.example.submission2.Response.DetailUserResponse
import com.example.submission2.Response.ItemsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): ViewModel() {
    private val _detailUsers = MutableLiveData<DetailUserResponse>()
    val detailUsers: LiveData<DetailUserResponse> = _detailUsers

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    val fav = MutableLiveData<DetailUserResponse>()

    private var favDao: FavoriteDao? = null
    private var favDtb: FavoriteDatabase? = null

    companion object{
        const val TAG = "DetailViewModel"
    }

    init {
        favDtb = FavoriteDatabase.getDatabase(application)
        favDao = favDtb?.favoriteDao()
    }

    private val mFavRepository: FavRepository = FavRepository(application)

    fun getAllNotes(): LiveData<List<Favorite>> = mFavRepository.getAllFav()

    fun insert(favorite: Favorite){
        mFavRepository.insert(favorite)
    }

    fun delete(favorite: Favorite){
        mFavRepository.delete(favorite)
    }

    fun getFavorite():LiveData<List<Favorite>> =
        mFavRepository.getAllFav()

    fun getDetailUsers(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(query)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUsers.value = response.body()
                } else {
                    Log.e(TAG, "onSucces: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowers(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(query)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(call: Call<List<ItemsItem>>, response: Response<List<ItemsItem>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("Response body: Follower", response.body()?.size.toString())
                    _followers.value = response.body()
                } else {
                    Log.e(TAG, "onSucces: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getfollowing(query)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(call: Call<List<ItemsItem>>, response: Response<List<ItemsItem>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("Response body: Following", response.body()?.size.toString())
                    _following.value = response.body()
                } else {
                    Log.e(TAG, "onSucces: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun addFavorite(id: Int, login: String, name: String, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val userF = Favorite(
                name,
                login,
                id,
                avatarUrl
            )
            favDao?.insertFavorite(userF)
        }
    }

//    suspend fun checkUser(id: Int) = favDao?.checkingFav(id)
//
//    fun deleteFavorite(id: Int) {
//        CoroutineScope(Dispatchers.IO).launch {
//            favDao?.deleteFavorite(id)
//        }
//    }
}