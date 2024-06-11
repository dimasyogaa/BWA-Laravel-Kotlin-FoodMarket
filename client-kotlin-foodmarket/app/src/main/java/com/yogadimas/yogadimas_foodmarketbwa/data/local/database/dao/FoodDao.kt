package com.yogadimas.yogadimas_foodmarketbwa.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.entities.FoodEntity

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(food: List<FoodEntity>)

    @Query("SELECT * FROM food")
    fun getAllFoods(): PagingSource<Int, FoodEntity>

    @Query("SELECT * FROM food")
    fun getAllFoodsNoPaging(): List<FoodEntity>

    @Query("SELECT * from food WHERE id = :id")
    fun getFoodById(id: Long): FoodEntity

    @Query("SELECT * from food WHERE types LIKE '%new_food%'")
    fun getAllFoodsByNewTaste():List<FoodEntity>

    @Query("SELECT * from food WHERE types LIKE '%popular%'")
    fun getAllFoodsByPopular(): List<FoodEntity>

    @Query("SELECT * from food WHERE types LIKE '%recommended%'")
    fun getAllFoodsByRecommended(): List<FoodEntity>

    @Delete
    suspend fun delete(food: FoodEntity)

    @Query("DELETE FROM food")
    suspend fun deleteAll()
}