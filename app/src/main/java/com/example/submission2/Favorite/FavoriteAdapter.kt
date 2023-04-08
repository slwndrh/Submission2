package com.example.submission2.Favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission2.Database.Favorite
import com.example.submission2.Repository.FavDiffCallback
import com.example.submission2.databinding.ItemFavBinding
import kotlinx.coroutines.*

class FavoriteAdapter(private val list: ArrayList<Favorite>): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: ArrayList<Favorite>){
        val diffCallback = FavDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
        diffResult.dispatchUpdatesTo(this)
    }

    inner class FavoriteViewHolder(private val binding: ItemFavBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(favorite: Favorite){
            binding.apply {
                Glide.with(itemView)
                    .load(favorite.avatar_url)
                    .centerCrop()
                    .into(ivFav)
                tvFavUsername.text = favorite.login
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view =  ItemFavBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(list[holder.adapterPosition])}
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(favorite: Favorite)
    }

    inner class NoteViewHolder(private val binding: ItemFavBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                tvFavUsername.text = favorite.login
                tvFavName.text = favorite.name
                cvFav.setOnClickListener {
                    val intent = Intent(it.context, FavoriteActivity::class.java)
                    intent.putExtra(FavoriteActivity.EXTRA_FAVORITE, favorite)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}
//FavoriteActivity.kt
//
//class FavoriteActivity : AppCompatActivity() {
//
//    private val list = ArrayList<User>()
//
//    private var _binding: ActivityFavoriteBinding? = null
//    private val binding get() = _binding!!
//
//    private lateinit var adapter: FavoriteAdapter
//    private lateinit var viewModel: FavoriteViewModel
//
//    @SuppressLint("NotifyDataSetChanged")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        adapter = FavoriteAdapter(list)
//        adapter.notifyDataSetChanged()
//        showLoading(true)
//
//        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
//
//        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: User) {
//                val moveIntent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
//                moveIntent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
//                moveIntent.putExtra(DetailUserActivity.EXTRA_ID, data.id)
//                moveIntent.putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
//                startActivity(moveIntent)
//            }
//        })
//        binding.apply {
//            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
//            rvUser.setHasFixedSize(true)
//            rvUser.adapter = adapter
//        }
//        viewModel.getFavoriteUser()?.observe(this, {
//            if (it != null) {
//                val list2 = mapList(it)
//                adapter.setList(list2)
//                showLoading(false)
//            }
//        })
//    }
//
//    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
//        val listUser = ArrayList<User>()
//        for (user in users) {
//            val userMapped = User(
//                user.login,
//                user.id,
//                user.avatar_url
//            )
//            listUser.add(userMapped)
//        }
//        return listUser
//    }
//
//    private fun showLoading(isLoading: Boolean) {
//        if (isLoading) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
//    }
//}


