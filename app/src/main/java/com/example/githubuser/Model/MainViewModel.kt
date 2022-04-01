package com.example.githubuser.Model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.githubuser.API.ApiConfig
import com.example.githubuser.Data.DataUserSearch
import com.example.githubuser.Data.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel() : ViewModel() {

    val _user = MutableLiveData<ArrayList<DataUserSearch>>()
    private val _isLoading = MutableLiveData<Boolean>()

    fun setSearchUser(Query : String){
        ApiConfig.getApiService()
            .getUser(Query)
            .enqueue(object  : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful){
                        _user.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")


                }

            })

    }
    fun getSearchUser() : LiveData<ArrayList<DataUserSearch>>{
        return _user

    }

}