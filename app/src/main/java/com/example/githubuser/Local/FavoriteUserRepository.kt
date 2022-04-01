package com.example.githubuser.Local

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.Room.DBFav
import com.example.githubuser.Room.DaoUserFav
import com.example.githubuser.Room.FvUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: DaoUserFav
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = DBFav.getDatabase(application)
        mFavoriteUserDao = db!!.favoriteUserDao()
    }
    fun getAllFavoriteUser(): LiveData<List<FvUser>> = mFavoriteUserDao.getUser()
    fun insert(favoriteUser: FvUser) {
        executorService.execute { mFavoriteUserDao.addFavorite(favoriteUser) }
    }
    fun delete(favoriteUser: FvUser) {
        executorService.execute { mFavoriteUserDao.delete(favoriteUser) }
    }

    fun deleteAll() {
        executorService.execute{mFavoriteUserDao.DeleteDataAll() }
    }

}