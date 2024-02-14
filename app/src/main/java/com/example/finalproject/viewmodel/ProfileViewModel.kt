package com.example.finalproject.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    fun setUsername(newUsername: String) {
        _username.value = newUsername
    }
}