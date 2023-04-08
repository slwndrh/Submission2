package com.example.submission2.Database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    //ekesekusi di background
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favorite: Favorite)

//    @Update
//    fun updateFavorite(favorite: Favorite)

    @Delete
    fun deleteFavorite(favorite: Favorite)

    //list user favorite
    @Query("SELECT * FROM favorite")
    fun getFav(): LiveData<List<Favorite>>

    //menambah favorite
    @Query("SELECT count(*) FROM favorite WHERE Favorite.id = :id")
    fun checkingFav(id: Int): Int
//
//    //menghapus favorite
//    @Query("DELETE FROM favorite WHERE Favorite.id = :id")
//    suspend fun deleteFav(id: Int): Int
}