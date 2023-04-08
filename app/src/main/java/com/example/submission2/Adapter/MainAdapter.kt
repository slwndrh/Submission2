package com.example.submission2.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission2.Database.Favorite
import com.example.submission2.Detail.DetailActivity
import com.example.submission2.Response.ItemsItem
import com.example.submission2.R
import com.example.submission2.Response.UserResponse

class MainAdapter(val list: List<ItemsItem>) : RecyclerView.Adapter<MainAdapter.ViewHolder>(){
    private lateinit var onItemClickCallBack: OnItemClickCallback

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val userName: TextView = view.findViewById(R.id.tv_username)
        val imgVw: ImageView = view.findViewById(R.id.iv_user)
    }

    override fun onCreateViewHolder (viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = list[position]
        holder.userName.text = user.login

        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .into(holder.imgVw)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_FAV, user.login)
            holder.itemView.context.startActivity(intent)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: UserResponse)
    }
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Favorite> =
            object : DiffUtil.ItemCallback<Favorite>() {
                override fun areItemsTheSame(oldFavorite: Favorite, newFavorite: Favorite): Boolean {
                    return oldFavorite.login == newFavorite.login
                }

                @SuppressLint("DiffUtilsEquals")
                override fun areContentsTheSame(oldFavorite: Favorite, newFavorite: Favorite): Boolean {
                    return oldFavorite == newFavorite
                }
            }
    }
}