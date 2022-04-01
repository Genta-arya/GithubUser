package com.example.githubuser.Adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.UI.FragmentFollower
import com.example.githubuser.UI.FragmentFollowing

class SectionsPagerAdapter(activity: AppCompatActivity, supportFragmentManager: FragmentManager, data : Bundle) : FragmentStateAdapter(activity) {

    private var fragmentBundle: Bundle
    init {
        fragmentBundle = data
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FragmentFollower()
            1 -> fragment = FragmentFollowing()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}