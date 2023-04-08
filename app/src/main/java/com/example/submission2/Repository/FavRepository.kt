package com.example.submission2.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.submission2.Database.API.ApiService
import com.example.submission2.Database.Favorite
import com.example.submission2.Database.FavoriteDao
import com.example.submission2.Database.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFav(): LiveData<List<Favorite>> = mFavoriteDao.getFav()

    fun insert(favorite: Favorite) {
        executorService.execute { mFavoriteDao.insertFavorite(favorite) }
    }
    fun delete(favorite: Favorite) {
        executorService.execute { mFavoriteDao.deleteFavorite(favorite) }
    }
//    fun update(favorite: Favorite) {
//        executorService.execute{ mFavoriteDao.updateFavorite(favorite)}
//    }
}