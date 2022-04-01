package com.example.githubuser.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuser.Model.DetailViewModel
import com.example.githubuser.Adapter.SectionsPagerAdapter
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {
    lateinit var binding :  ActivityDetailUserBinding
    lateinit var viewModel : DetailViewModel

    companion object{
        val usernameObject = "username"
        val idObject = "id"
        val avatarObject = "avatar"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail User"
//        Submisi 1

        val username = intent.getStringExtra(usernameObject)
        val id = intent.getIntExtra(idObject,0)
        val avatar_url = intent.getStringExtra(avatarObject)


        val bundle = Bundle()
        bundle.putString(usernameObject,username)

        val sectionAdapter = SectionsPagerAdapter(this, supportFragmentManager, bundle)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(DetailUserActivity.TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        viewModel.setUserDetail(username!!)
        viewModel.getUserDetail().observe(this,{
            if (it != null){
                binding.apply {
                    tvNameSearchs.text = it.name
                    tvAddressSearch.text=it.location
                    tvUsernameText.text = it.login
                    tvFollowersText.text = "${it.followers} followers"
                    tvFollowingText.text = "${it.following} following"
                    tvCompanyText.text = it.company
                    tvRepoSearch.text= it.public_repos.toString()
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .circleCrop()
                        .into(fotoUserSearch)
                }
            }
        })

        var isCek = false
        CoroutineScope(Dispatchers.IO).launch {
            val cek = viewModel.cekId(id)
            withContext(Dispatchers.Main){
                if ( cek != null){
                    if (cek > 0){

                        binding.toggleButton.isChecked = true
                        isCek =true
                        

                    }else{
                        binding.toggleButton.isChecked = false
                        isCek= false
                    }
                }
            }
        }
        binding.toggleButton.setOnClickListener {
            isCek = !isCek
            if (isCek){
                if (avatar_url != null) {
                    viewModel.addFavorite(username, id , avatar_url)
                }
                Toast.makeText(this,"Favorite sudah di tambahkan",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.removeFv(id)
                Toast.makeText(this,"Favorite sudah di hapus",Toast.LENGTH_SHORT).show()
            }
            binding.toggleButton.isChecked = isCek
        }
    }

}
