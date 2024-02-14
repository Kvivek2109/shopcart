package com.example.finalproject.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.user.User
import com.example.finalproject.model.user.UserRepository
import com.example.finalproject.model.user.UserRoomDatabase
import com.example.finalproject.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private lateinit var userRepository: UserRepository

    private val _login = MutableSharedFlow<Resource<User>>()
    val login = _login.asSharedFlow()

    fun login(context: Context,email:String, password: String) {
        viewModelScope.launch { _login.emit(Resource.Loading()) }
        userRepository = UserRepository(UserRoomDatabase.getInstance(context).userDao())
        viewModelScope.launch {
            val user = userRepository.getUserByUsername(email)
            if (user != null && user.password == password) {
                _login.emit(Resource.Success(user))
            } else {
                _login.emit(Resource.Error("Invalid email or password"))
            }
        }
    }
}