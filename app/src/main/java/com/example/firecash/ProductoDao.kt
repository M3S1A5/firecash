package com.example.firecash

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface ProductoDao {
    @Query("SELECT * FROM ProductoEntity")
    fun getAll(): List<ProductoEntity>

    @Insert
    fun insert(producto: ProductoEntity)

    @Update
    fun update(producto: ProductoEntity)

    @Delete
    fun delete(producto: ProductoEntity)
}