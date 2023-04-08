package com.example.submission2.Repository

import androidx.recyclerview.widget.DiffUtil
import com.example.submission2.Database.Favorite

class FavDiffCallback(private val mOldList: List<Favorite>, private val mNewList: List<Favorite>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldList.size
    }

    override fun getNewListSize(): Int {
        return mNewList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldList[oldItemPosition].id == mNewList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = mOldList[oldItemPosition]
        val newUser = mNewList[newItemPosition]
        return oldUser.login == newUser.login && oldUser.name == newUser.name
    }
}