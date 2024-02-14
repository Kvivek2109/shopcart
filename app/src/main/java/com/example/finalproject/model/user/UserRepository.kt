package com.example.finalproject.model.user


class UserRepository(private val userDao: UserDao) {

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun insertMultipleUsers(users: List<User>) {
        for (user in users) {
            insert(user)
        }
    }

    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }
}