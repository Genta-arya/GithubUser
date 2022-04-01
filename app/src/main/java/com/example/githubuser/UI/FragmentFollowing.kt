package com.example.githubuser.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.Model.following_ViewModel
import com.example.githubuser.Adapter.SearchAdapter
import com.example.githubuser.R
import com.example.githubuser.databinding.FragmentFollowingBinding



class FragmentFollowing: Fragment(R.layout.fragment_following){

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: following_ViewModel
    private lateinit var adapter: SearchAdapter
    private lateinit var username : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUserActivity.usernameObject).toString()
        _binding = FragmentFollowingBinding.bind(view)
        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            listRvFollower.setHasFixedSize(true)
            listRvFollower.layoutManager = LinearLayoutManager(activity)
            listRvFollower.adapter = adapter

        }
        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            following_ViewModel::class.java)
        viewModel.setListfollowing(username)
        viewModel.getListfollowing().observe(viewLifecycleOwner,{
            if (it!= null){
                adapter.setDataList(it)
                showLoading(false)
            }
        })

    }
    private fun showLoading(b: Boolean) {

        if (b){
            binding.progressBarr.visibility = View.VISIBLE
        }else{
            binding.progressBarr.visibility = View.GONE
        }

    }
}