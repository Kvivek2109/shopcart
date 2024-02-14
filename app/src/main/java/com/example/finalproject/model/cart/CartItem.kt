package com.example.finalproject.model.cart

import androidx.room.Entity
import androidx.room.ColumnInfo

@Entity(tableName = "cart_items", primaryKeys = ["user", "itemId"])
data class CartItem(
    @ColumnInfo(name = "user") val user: String,
    @ColumnInfo(name = "itemId") val itemId: Int,
    @ColumnInfo(name = "quantity") var quantity: Int
)