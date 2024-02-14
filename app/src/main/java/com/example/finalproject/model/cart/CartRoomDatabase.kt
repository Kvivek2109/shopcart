package com.example.finalproject.model.cart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartItem::class], version = 1, exportSchema = false)
abstract class CartRoomDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: CartRoomDatabase? = null

        fun getInstance(context: Context): CartRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CartRoomDatabase::class.java,
                    "cart_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

