package com.shopscale.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shopscale.core.database.dao.ProductDao
import com.shopscale.core.database.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ShopScaleDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
