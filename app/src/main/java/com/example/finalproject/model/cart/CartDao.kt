package com.example.finalproject.model.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrUpdateCartItem(cartItem: CartItem)

    @Query("SELECT * FROM cart_items WHERE user = :user")
    fun getCartItemsForUser(user: String): Flow<List<CartItem>>

    @Query("SELECT * FROM cart_items WHERE user = :user AND itemId = :itemId")
    suspend fun getCartItem(user: String, itemId: Int): CartItem?

    @Query("UPDATE cart_items SET quantity = :quantityToUpdate WHERE user = :user AND itemId = :itemId")
    suspend fun updateCartItemQuantity(user: String, itemId: Int, quantityToUpdate: Int)

    @Query("INSERT INTO cart_items (user, itemId, quantity) VALUES (:user, :itemId, :quantity)")
    suspend fun insertNewCartItem(user: String, itemId: Int, quantity: Int)

    @Query("DELETE FROM cart_items WHERE user = :user AND itemId = :itemId")
    suspend fun deleteCartItem(user: String, itemId: Int)

    @Query("DELETE FROM cart_items WHERE user = :user")
    suspend fun clearCart(user: String)

    @Query("SELECT COUNT(*) FROM cart_items WHERE user = :user AND itemId = :itemId")
    suspend fun getUserItemCount(user: String, itemId: Int): Int

    @Query("SELECT COUNT(*) FROM cart_items WHERE user = :user")
    suspend fun getUserCount(user: String): Int
}