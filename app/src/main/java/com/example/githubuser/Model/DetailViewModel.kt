package com.example.githubuser.Model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuser.API.ApiConfig
import com.example.githubuser.Room.DBFav
import com.example.githubuser.Room.DaoUserFav
import com.example.githubuser.Room.FvUser
import com.example.githubuser.Data.UserDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class DetailViewModel(Aplikasi: Application) : AndroidViewModel(Aplikasi) {
    val user = MutableLiveData<UserDetail>()
    private val _isLoading = MutableLiveData<Boolean>()

    private var daouser : DaoUserFav?
    private var dbuser : DBFav?

    init{
        dbuser=DBFav.getDatabase(Aplikasi)
        daouser= dbuser!!.favoriteUserDao()
    }


    fun setUserDetail(username: String) {
        ApiConfig.getApiService()
            .getUserDetail(username)
            .enqueue(object : Callback<UserDetail> {
                override fun onResponse(
                    call: Call<UserDetail>, response: Response<UserDetail>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                    _isLoading.value = false
                    t.message?.let { Log.d("failure", it) }
                }

            })

    }

    fun getUserDetail(): LiveData<UserDetail> {
        return user

    }
    fun addFavorite(username: String,id: Int , avatar_url:String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FvUser(
                username,
                id,
                avatar_url
            )
           daouser?.addFavorite(user)
        }

    }
    fun cekId(id: Int) = daouser?.cekUserId(id)

    
    fun removeFv(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            daouser?.DeleteData(id)
        }

    }


}