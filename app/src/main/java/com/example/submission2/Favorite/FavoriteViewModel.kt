package com.example.submission2.Favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.submission2.Database.Favorite
import com.example.submission2.Database.FavoriteDao
import com.example.submission2.Database.FavoriteDatabase
import com.example.submission2.Repository.FavRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application)  {
    private var favDao: FavoriteDao? = null
    private var favDtbs: FavoriteDatabase? = null

    private val mFavRepository: FavRepository = FavRepository(application)

    init {
        favDtbs = FavoriteDatabase.getDatabase(application)
        favDao = favDtbs?.favoriteDao()
    }

    fun getFavoriteUser(): LiveData<List<Favorite>>? {
        return favDao?.getFav()
    }

    fun insert(favorite: Favorite) {
        mFavRepository.insert(favorite)
    }

//    fun update(favorite: Favorite) {
//        mFavRepository.update(favorite)
//    }

    fun delete(favorite: Favorite) {
        mFavRepository.delete(favorite)
    }
}