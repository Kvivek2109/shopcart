package com.example.finalproject.model.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table WHERE email = :email")
    suspend fun getUserByUsername(email: String): User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)
}