package com.example.finalproject.model.cart

import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {

    suspend fun insertOrUpdateCartItem(cartItem: CartItem) {
        cartDao.insertOrUpdateCartItem(cartItem)
    }

    suspend fun getCartItem(user: String, itemId: Int): CartItem? {
        return cartDao.getCartItem(user, itemId)
    }

    fun getCartItemsForUser(user: String): Flow<List<CartItem>> {
        return cartDao.getCartItemsForUser(user)
    }

    suspend fun deleteCartItem(user: String, itemId: Int) {
        cartDao.deleteCartItem(user, itemId)
    }

    suspend fun clearCart(user: String) {
        cartDao.clearCart(user)
    }

    suspend fun increaseQuantity(user: String, itemId: Int, quantityToAdd: Int) {
        val existingCartItem = cartDao.getCartItem(user, itemId)

        if (existingCartItem != null) {
            val newQuantity = existingCartItem.quantity + quantityToAdd
            cartDao.updateCartItemQuantity(user, itemId, newQuantity)
        } else {
            cartDao.insertNewCartItem(user, itemId, quantityToAdd)
        }
    }

    suspend fun decreaseQuantity(user: String, itemId: Int, quantityToRemove: Int) {
        val existingCartItem = cartDao.getCartItem(user, itemId)

        if (existingCartItem != null && existingCartItem.quantity > 1) {
            val newQuantity = existingCartItem.quantity - quantityToRemove
            cartDao.updateCartItemQuantity(user, itemId, newQuantity)
        } else if (existingCartItem != null && existingCartItem.quantity == 1) {
            cartDao.deleteCartItem(user, itemId)
        }
    }

    suspend fun doesUserItemExist(user: String, itemId: Int): Boolean {
        return cartDao.getUserItemCount(user, itemId) > 0
    }
}
