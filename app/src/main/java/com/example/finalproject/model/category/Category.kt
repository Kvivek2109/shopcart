package com.example.finalproject.model.category

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: String,
    val name: String,
    val src: String,
): Parcelable {
    constructor():this("0","","")
}

class CategoryList {
    private val categories: List<Category> = listOf(
        Category("0", "Sweaters", "@drawable/sweater"),
        Category("1", "Winter Cap", "@drawable/wintercap"),
        Category("2", "Snow Boots", "@drawable/snowboots"),
        Category("3", "Sneakers", "@drawable/sneakers"),
        Category("4", "Socks", "@drawable/socks"),
        Category("5", "Bag", "@drawable/bag"),
        Category("6", "Sandals", "@drawable/sandals"),
        Category("7", "Perfume", "@drawable/perfume"),
        Category("8", "Christmas Gifts", "@drawable/christmasgifts")
    )

    companion object {
        fun getCategoryList(): List<Category>  {
            val categoryListInstance = CategoryList()
            return categoryListInstance.categories
        }
    }
}