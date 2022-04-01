package com.example.githubuser.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.Adapter.FavoriteAdapter
import com.example.githubuser.Model.FavoriteViewModel
import com.example.githubuser.Model.ViewModels
import com.example.githubuser.R
import com.example.githubuser.Room.FvUser
import com.example.githubuser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {


    private lateinit var favoriteUserviewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter
    private val binding: ActivityFavoriteBinding get() = _binding

    private var id: Int = 0
    private var isDelete = false
    lateinit var _binding: ActivityFavoriteBinding
    val favoriteuser: FvUser? get() = _favoriteUser
    private val _favoriteUser: FvUser? = null

    companion object {
         val ALERT_DIALOG_CLOSE = 0
         val ALERT_DIALOG_DELETE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        supportActionBar?.title = "Favorite User"
        setContentView(binding.root)
        adapter = FavoriteAdapter()
        favoriteUserviewModel = obtainViewModel(this)
        adapter.setOnClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FvUser) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).let {
                    it.putExtra(DetailUserActivity.usernameObject, data.login)
                    it.putExtra(DetailUserActivity.avatarObject, data.avatar_url)
                    it.putExtra(DetailUserActivity.idObject,data.id)
                    startActivity(it)
                }
            }
        })


        binding.apply {
            listFavorite.setHasFixedSize(true)
            listFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            listFavorite.adapter = adapter
        }

        favoriteUserviewModel.getAllFavUser().observe(this, {
            if (it != null) {
                adapter.setListFavUsers(it)
            } else {
                isDelete = false
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding!!
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val checkData = favoriteUserviewModel.getAllFavUser()
        val tvEmpty: TextView = findViewById(R.id.tv_empty)
        checkData.observe(this, {
            if (it.isEmpty()) {
                tvEmpty.visibility = View.VISIBLE
            } else {
                menuInflater.inflate(R.menu.option_delete, menu)
                tvEmpty.visibility = View.GONE
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_delete -> {
                showAlertDialogRemoveAllData(ALERT_DIALOG_DELETE)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialogRemoveAllData(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = ""
            dialogMessage = ""
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    favoriteUserviewModel.removeAllData()
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModels.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

}