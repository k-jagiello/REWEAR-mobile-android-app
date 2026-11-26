package com.example.rewear.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.rewear.data.local.entity.ClothingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClothingDao {

    @Insert
    suspend fun insert(clothing: ClothingEntity)

    @Query("SELECT * FROM clothes")
    fun getAll(): Flow<List<ClothingEntity>>

    @Update
    suspend fun update(clothes: ClothingEntity)

    @Delete
    suspend fun delete(clothes: ClothingEntity)
}