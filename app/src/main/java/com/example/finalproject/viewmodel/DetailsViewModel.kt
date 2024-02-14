package com.example.finalproject.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.cart.CartItem
import com.example.finalproject.model.cart.CartRepository
import com.example.finalproject.model.cart.CartRoomDatabase
import com.example.finalproject.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val _addToCart = MutableStateFlow<Resource<CartItem>>(Resource.Unspecified())
    val addToCart = _addToCart.asStateFlow()
    val user: String? = null

    private lateinit var cartRepository: CartRepository

    fun addProductInCart(context: Context, username: String, itemId: Int, quantity: Int) {
        cartRepository = CartRepository(CartRoomDatabase.getInstance(context).cartDao())
        viewModelScope.launch {
            val exists = cartRepository.doesUserItemExist(username, itemId)
            if (exists) {
                cartRepository.increaseQuantity(username, itemId, quantity)
            }
            else {
                cartRepository.insertOrUpdateCartItem(CartItem(username, itemId, quantity))
            }
            _addToCart.emit(Resource.Success(CartItem(username, itemId, quantity)))
        }
    }
}