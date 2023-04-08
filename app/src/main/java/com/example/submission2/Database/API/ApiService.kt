package com.example.submission2.Database.API

import com.example.submission2.Response.DetailUserResponse
import com.example.submission2.Response.ItemsItem
import com.example.submission2.Response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") q: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") login: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") login: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getfollowing(
        @Path("username") login: String
    ): Call<List<ItemsItem>>
}