package com.example.githubuser.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.API.ApiConfig
import com.example.githubuser.Data.DataUserSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class follower_ViewModel : ViewModel() {
    val listFollower = MutableLiveData<ArrayList<DataUserSearch>>()

    fun setListFollower(username : String){
        ApiConfig.getApiService()
            .getUserFollower(username)
            .enqueue(object : Callback<ArrayList<DataUserSearch>>{
                override fun onResponse(
                    call: Call<ArrayList<DataUserSearch>>,
                    response: Response<ArrayList<DataUserSearch>>
                ) {
                    if (response.isSuccessful){
                        listFollower.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<DataUserSearch>>, t: Throwable) {
                    Log.d("failure",t.message!!)
                }

            })
    }
    fun getListFollower():LiveData<ArrayList<DataUserSearch>>{
        return listFollower
    }
}