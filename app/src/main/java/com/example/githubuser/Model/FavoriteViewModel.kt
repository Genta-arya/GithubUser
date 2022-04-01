package com.example.githubuser.Model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.Local.FavoriteUserRepository
import com.example.githubuser.Room.FvUser


class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)
    fun getAllFavUser(): LiveData<List<FvUser>> = mFavoriteUserRepository.getAllFavoriteUser()

    fun insert(favoriteUser: FvUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }
    fun delete(favoriteUser: FvUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }

    fun removeAllData() = mFavoriteUserRepository.deleteAll()
}