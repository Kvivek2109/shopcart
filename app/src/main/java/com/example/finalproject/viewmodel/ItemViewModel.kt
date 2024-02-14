package com.example.finalproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.category.Category
import com.example.finalproject.model.item.Item
import com.example.finalproject.model.item.ItemList
import com.example.finalproject.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel() {

    private val _item = MutableStateFlow<Resource<List<Item>>>(Resource.Unspecified())
    val item: StateFlow<Resource<List<Item>>> = _item

    private val _category = MutableStateFlow<Resource<Category>>(Resource.Unspecified())
    val category = _category.asStateFlow()

    fun fetchItemsByCategory(category: Category) {
        viewModelScope.launch {
            _item.emit(Resource.Loading())
        }
        val items = ItemList.getItemsByCategory(category.name)
        viewModelScope.launch {
            _item.emit(Resource.Success(items))
        }
    }
}