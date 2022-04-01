package com.example.githubuser.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.Model.follower_ViewModel
import com.example.githubuser.Adapter.SearchAdapter
import com.example.githubuser.R
import com.example.githubuser.databinding.FragmentFollowerBinding


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class FragmentFollower : Fragment(R.layout.fragment_follower) {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: follower_ViewModel
    private lateinit var adapter: SearchAdapter
    private lateinit var username : String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Inflate the layout for this fragment
        val args = arguments
        username = args?.getString(DetailUserActivity.usernameObject).toString()
        _binding = FragmentFollowerBinding.bind(view)
        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            listRvFollower.setHasFixedSize(true)
            listRvFollower.layoutManager = LinearLayoutManager(activity)
            listRvFollower.adapter = adapter

        }
        showLoading(true)
        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(
            follower_ViewModel::class.java)
        viewModel.setListFollower(username)
        viewModel.getListFollower().observe(viewLifecycleOwner,{
            if (it!= null){
                adapter.setDataList(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showLoading(b: Boolean) {

        if (b){
            binding.progressBarr.visibility = View.VISIBLE
        }else{
            binding.progressBarr.visibility = View.GONE
        }

    }

}