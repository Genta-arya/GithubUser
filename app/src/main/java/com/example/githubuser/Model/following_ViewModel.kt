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

class following_ViewModel : ViewModel() {
    val listfollowing = MutableLiveData<ArrayList<DataUserSearch>>()

    fun setListfollowing(username : String){
        ApiConfig.getApiService()
            .getUserFollowing(username)
            .enqueue(object : Callback<ArrayList<DataUserSearch>>{
                override fun onResponse(
                    call: Call<ArrayList<DataUserSearch>>,
                    response: Response<ArrayList<DataUserSearch>>
                ) {
                    if (response.isSuccessful){
                        listfollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<DataUserSearch>>, t: Throwable) {
                    Log.d("failure",t.message!!)
                }

            })
    }
    fun getListfollowing():LiveData<ArrayList<DataUserSearch>>{
        return listfollowing
    }
}