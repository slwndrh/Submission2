package com.example.submission2.Database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
data class Favorite (
    val login: String,
    val name: String,
    @PrimaryKey
    val id: Int,
    val avatar_url: String

//    @PrimaryKey(autoGenerate = false)
//    @ColumnInfo(name = "id")
//    var id: Int = 0,
//
//    @ColumnInfo(name = "login")
//    var login: String = "",
//
//    @ColumnInfo(name = "name")
//    var name: String = "",
//
//    @ColumnInfo(name = "avatarUrl")
//    var avatarUrl: String? = null
): java.io.Serializable