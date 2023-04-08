package com.example.submission2.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submission2.Response.DetailUserResponse
import com.example.submission2.R
import com.example.submission2.Adapter.ViewPagerAdapter
import com.example.submission2.Database.Favorite
import com.example.submission2.Favorite.FavoriteAdapter
import com.example.submission2.Response.UserResponse
import com.example.submission2.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavoriteAdapter

    private lateinit var detailViewModel: DetailViewModel
    private var name: String? = null

    companion object {
        const val EXTRA_FAV = "extra_fav"
        const val EXTRA_USER = "extra_user"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_LOGIN = "extra_login"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_AV = "extra_avatar"

        private val TAB_CODES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        val user = intent.getStringExtra(EXTRA_FAV)
        binding.tvUserlogin.text = user

        name = intent.getStringExtra(EXTRA_NAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val login = intent.getStringExtra(EXTRA_LOGIN)
        val bundle = Bundle()
        bundle.putString(EXTRA_NAME, name)
        showLoading(true)

        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPagerAdapter.username = user.toString()
        val viewPage: ViewPager2 = findViewById(R.id.viewPager)
        viewPage.adapter = viewPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPage) { tab, position ->
            tab.text = resources.getString(TAB_CODES[position])
        }.attach()

        if (user != null) {
            showLoading(true)
            detailViewModel.getDetailUsers(user)
            showLoading(false)
        }

        detailViewModel.detailUsers.observe(this) {
            setUserDetail(it)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setUserDetail(Name: DetailUserResponse) {
        Glide.with(this@DetailActivity)
            .load(Name.avatarUrl)
            .into(binding.ivDetailuser)
        binding.tvUserlogin.text = Name.login
        binding.tvName.text = Name.name
        binding.tvFollower.text = Name.followers.toString()
        binding.tvFollowing.text = Name.following.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbDetail.visibility = View.VISIBLE
        } else {
            binding.pbDetail.visibility = View.GONE
        }
    }

    private fun tabLayout() {
        val name = intent.getStringExtra(EXTRA_NAME)

        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPagerAdapter.username = name.toString()
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        viewPager.adapter = viewPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_CODES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }
}

//        detailViewModel.error.observe(this) {
//            Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show()
//            detailViewModel.error()
//        }


//        var _isChecking = false
//        CoroutineScope(Dispatchers.IO).launch {
//            val count = detailViewModel.checkUser(id)
//            withContext(Dispatchers.Main) {
//                if (count != null) {
//                    if (count > 0) {
//                        binding.fabFav.isCheck = true
//                        _isChecking = true
//                    } else {
//                        binding.fabFav.isCheck = false
//                        _isChecking = false
//                    }
//                }
//            }
//
//            binding.fabFav.setOnClickListener {
//                _isChecking = !_isChecking
//                if (_isChecking) {
//                    detailViewModel.addFavorite(
//                        id,
//                        name.toString(),
//                        login.toString(),
//                        avatarUrl = String()
//                    )
//                    Toast.makeText(this, "Favorit $name", Toast.LENGTH_SHORT).show()
//                } else {
//                    detailViewModel.deleteFavorite(id)
//                    Toast.makeText(this, "Menghapus Favorit $name", Toast.LENGTH_SHORT).show()
//                }
//                binding.fabFav.i = _isChecking
//            }
//        }
//        tabLayout()
//    }



//    fun insertToDatabase(detailList: UserResponse) {
//        insertToDatabase().let { favoriteUser ->
//            favoriteUser.id = detailList.id
//            favoriteUser.login = detailList.login
//            favoriteUser.imageUrl = detailList.avatarUrl
//            viewModel.insert(favoriteUser as Favorite)
//            Toast.makeText(this, "Favorited", Toast.LENGTH_SHORT).show()
//        }
//    }
