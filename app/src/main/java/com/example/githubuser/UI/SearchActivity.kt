package com.example.githubuser.UI

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.Model.MainViewModel
import com.example.githubuser.Adapter.SearchAdapter
import com.example.githubuser.Data.DataUserSearch
import com.example.githubuser.Local.Preference
import com.example.githubuser.Model.PrefViewModel
import com.example.githubuser.Model.ViewModelFactory
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    lateinit var adapter: SearchAdapter
    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivitySearchBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme")
    lateinit var model : PrefViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Search User"
        val pref = Preference.getInstance(dataStore)
        model = ViewModelProvider(this, ViewModelFactory(pref)).get(
            PrefViewModel::class.java
        )
        model.getTheme().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })
        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()
        adapter.setonItemClick(object : SearchAdapter.onItemClickCall {
            override fun onItemClicked(user: DataUserSearch) {
                Intent(this@SearchActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.usernameObject,user.login)
                    it.putExtra(DetailUserActivity.idObject,user.id)
                    it.putExtra(DetailUserActivity.avatarObject,user.avatar_url)
                    startActivity(it)
                }
            }

        })
        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        binding.apply setOnKeyListener@{
            listUserSearch.layoutManager = LinearLayoutManager(this@SearchActivity)
            listUserSearch.setHasFixedSize(true)
            listUserSearch.adapter = adapter

            btnCari.setOnClickListener{
                searchuser()

            }
            query.setOnKeyListener { v , keyCode, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    searchuser()
                    return@setOnKeyListener true
                }
                //COMANND

                return@setOnKeyListener false
            }
        }

        viewModel.getSearchUser() .observe(this,{
            if (it!= null){
                adapter.setDataList(it)
                showLoading(false)
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.cari-> {
                val i = Intent(this, SearchActivity::class.java)
                startActivity(i)
                return true

            }
            R.id.theme_dark->{
                model.saveThemes(true)
                Toast.makeText(this,"Dark Mode telah Aktif", Toast.LENGTH_SHORT).show()
            }
            R.id.theme_light-> {
                model.saveThemes(false)
                Toast.makeText(this,"Light Mode telah Aktif", Toast.LENGTH_SHORT).show()
            }
            R.id.favorite_user->{
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)

            }


        }
        return true
    }
    fun searchuser(){
        binding.apply {
            val query =  query.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUser(query)
        }
    }

    private fun showLoading(b: Boolean) {

        if (b){
            binding.progressBarrSearch.visibility = View.VISIBLE
        }else{
            binding.progressBarrSearch.visibility = View.GONE
        }

    }
}