package com.example.firecash

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface VentaDao {
    @Query("SELECT * FROM VentaEntity")
    fun getAll(): List<VentaEntity>

    @Insert
    fun insert(venta: VentaEntity)

    @Update
    fun update(venta: VentaEntity)

    @Delete
    fun delete(venta: VentaEntity)
}