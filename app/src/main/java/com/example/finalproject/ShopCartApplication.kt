package com.example.finalproject

import android.app.Application
import com.example.finalproject.model.user.User
import com.example.finalproject.model.user.UserRepository
import com.example.finalproject.model.user.UserRoomDatabase
import kotlinx.coroutines.runBlocking

class ShopCartApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val userRepository = UserRepository(UserRoomDatabase.getInstance(this).userDao())

        val users = listOf(
            User("user1", "password1"),
            User("user2", "password2"),
            User("test@gmail.com", "pass123"),
            User("user10", "password10")
        )

        runBlocking {
            userRepository.insertMultipleUsers(users)
        }
    }
}