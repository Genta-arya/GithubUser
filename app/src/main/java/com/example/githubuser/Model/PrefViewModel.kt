package com.example.githubuser.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuser.Local.Preference
import kotlinx.coroutines.launch

class PrefViewModel(private val pref: Preference) : ViewModel()  {

    fun getTheme(): LiveData<Boolean> {
        return pref.getTheme().asLiveData()
    }

    fun saveThemes(DarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveTheme(DarkModeActive)
        }
    }
}