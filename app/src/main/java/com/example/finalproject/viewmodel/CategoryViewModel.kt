package com.example.finalproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.category.Category
import com.example.finalproject.model.category.CategoryList
import com.example.finalproject.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val _category = MutableStateFlow<Resource<List<Category>>>(Resource.Unspecified())
    val category: StateFlow<Resource<List<Category>>> = _category

    init {
        fetchCategory()
    }

    private fun fetchCategory() {
        viewModelScope.launch {
            _category.emit(Resource.Loading())
        }
        val categories = CategoryList.getCategoryList()
        viewModelScope.launch {
            _category.emit(Resource.Success(categories))
        }
    }
}