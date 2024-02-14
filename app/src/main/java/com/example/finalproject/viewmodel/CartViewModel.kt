package com.example.finalproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.cart.CartItem
import com.example.finalproject.model.cart.CartRepository
import com.example.finalproject.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val _cartItem = MutableStateFlow<Resource<List<CartItem>>>(Resource.Unspecified())
    val cartItem : StateFlow<Resource<List<CartItem>>> = _cartItem

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private lateinit var cartRepository: CartRepository

    fun initCartRepository(repository: CartRepository) {
        this.cartRepository = repository
    }
    fun setUsername(newUsername: String) {
        _username.value = newUsername
    }

    fun getCartItems() {
        viewModelScope.launch {
            try {
                _cartItem.value = Resource.Loading()
                cartRepository.getCartItemsForUser(username.value).collect {
                    if (it.isNotEmpty()) {
                        _cartItem.value = Resource.Success(it)
                    } else {
                        _cartItem.value = Resource.Error("Cart is Empty")
                    }
                }
            } catch (e: Exception) {
                _cartItem.emit(Resource.Error("Error fetching cart items."))
            }
        }
    }

    fun deleteCartItem(item: CartItem) {
        viewModelScope.launch {
            cartRepository.deleteCartItem(username.value, item.itemId)
        }
    }

    fun increaseQuantity (item: CartItem ) {
        viewModelScope.launch {
            cartRepository.increaseQuantity(username.value, item.itemId, 1)
        }
    }

    fun decreaseQuantity(item: CartItem ) {
        viewModelScope.launch {
            cartRepository.decreaseQuantity(username.value, item.itemId, 1)
        }
    }
}