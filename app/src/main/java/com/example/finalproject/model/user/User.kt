package com.example.finalproject.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "email") val username: String,
    @ColumnInfo(name = "password") val password: String
)