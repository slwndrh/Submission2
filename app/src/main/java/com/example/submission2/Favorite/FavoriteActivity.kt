package com.example.submission2.Favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.Database.Favorite
import com.example.submission2.Detail.DetailActivity
import com.example.submission2.databinding.ActivityFavoriteBinding

//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class FavoriteActivity : AppCompatActivity() {
    private val listFav = ArrayList<Favorite>()

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavoriteAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    companion object{
        const val EXTRA_FAVORITE = "extra_favorite"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorites"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = FavoriteAdapter(listFav)
        adapter.notifyDataSetChanged()
        showLoading(true)

        favoriteViewModel = obtainViewModel(this@FavoriteActivity)

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(favorite: Favorite) {
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, favorite.id)
                intent.putExtra(DetailActivity.EXTRA_LOGIN, favorite.login)
                intent.putExtra(DetailActivity.EXTRA_NAME, favorite.name)
                intent.putExtra(DetailActivity.EXTRA_AV, favorite.avatar_url)
                startActivity(intent)
            }
        })

        binding.apply {
            rvFav.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFav.setHasFixedSize(true)
            rvFav.adapter = adapter
        }
        favoriteViewModel.getFavoriteUser()?.observe(this, {
            if (it != null) {
                val list2 = mapList(it)
                adapter.setList(list2)
                showLoading(false)
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun mapList(fav: List<Favorite>): ArrayList<Favorite> {
        val listFavorite = ArrayList<Favorite>()
        for (favUser in fav) {
            val userMapped = Favorite(
                favUser.name,
                favUser.login,
                favUser.id,
                favUser.avatar_url
            )
            listFavorite.add(userMapped)
        }
        return listFavorite
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.fvNotfound.visibility = View.GONE
            binding.rvFav.visibility = View.GONE
            binding.pbFav.visibility = View.VISIBLE
        } else {
            binding.fvNotfound.visibility = View.GONE
            binding.rvFav.visibility = View.VISIBLE
            binding.pbFav.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getFavoriteUser()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

//        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
//            override fun onItemClicked(favorite: Favorite) {
//                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
//                intent.putExtra(DetailActivity.EXTRA_USER, favorite.login)
//                startActivity(intent)
//            }
//        })

//        favoriteViewModel.getFavoriteUser()?.observe(this) { users ->
//            if (users != null) {
//                adapter.setFavorite(users)
//            }
//        }
//
//        favoriteViewModel.getFavoriteUser().observe(this){
//            if (.isE)
//        }
//
//        val items = arrayListOf<ItemsItem>()
//
//
//        val pref = SettingPreferences.getInstance(dataStore)
//        val settingViewModel = ViewModelProvider(this, SettingModelFactory(pref)).get(
//            SettingViewModel::class.java
//        )
//        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
//            if (isDarkModeActive) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            }
//
//        }
//    }

//    private var _binding : ActivityFavoriteBinding? = null
//    private val binding get() = _binding
//    private lateinit var adapter : FavoriteAdapter
//
//    private lateinit var favoriteViewModel: FavoriteViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
//        setContentView(binding?.root)
//
//        favoriteViewModel = obtainViewModel(this@FavoriteActivity)
//        favoriteViewModel.getFavorite().observe(this) { githubUserList ->
//            if (githubUserList != null) {
//                adapter.setFavorite(githubUserList)
//
//            }
//        }
//        adapter = FavoriteAdapter()
//        binding?.rvFav?.layoutManager = LinearLayoutManager(this)
//        binding?.rvFav?.setHasFixedSize(false)
//        binding?.rvFav?.adapter = adapter
//    }
//
//    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
//        val factory = DetailViewModelFactory.getInstance(activity.application)
//        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
//    }
//
//}