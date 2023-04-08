package com.example.submission2.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.Adapter.MainAdapter
import com.example.submission2.Detail.DetailViewModel
import com.example.submission2.Response.ItemsItem
import com.example.submission2.Theme.ThemeModelFactory
import com.example.submission2.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var detailViewModel: DetailViewModel

//    private val detailViewModel by viewModels<DetailViewModel>{
//        ThemeModelFactory.getInstance(requireActivity())
//    }

    companion object{
        const val ARG_POSITION = "arg_number"
        const val ARG_USERNAME = "arg_username"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var position = arguments?.getInt(ARG_POSITION, 0)
        var username = arguments?.getString(ARG_USERNAME)

        Log.d("arguments: position", position.toString())
        Log.d("arguments: username", username.toString())

//        detailViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1){
            showLoadingUser(true)
            username?.let {detailViewModel.getFollowers(it)}
            detailViewModel.followers.observe(viewLifecycleOwner){
                setFollow(it)
                showLoadingUser(false)
            }
        } else {
            showLoadingUser(true)
            username?.let { detailViewModel.getFollowing(it) }
            detailViewModel.following.observe(viewLifecycleOwner){
                setFollow(it)
                showLoadingUser(false)
            }
        }
    }

    private fun setFollow(list: List<ItemsItem>) {
        binding.apply {
            binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
            val adapter = MainAdapter(list)
            binding.rvFollow.adapter = adapter
        }
    }

    private fun showLoadingUser(isLoading: Boolean) {
        if (isLoading) {
            binding.pbFollow.visibility = View.VISIBLE
        } else {
            binding.pbFollow.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}