package com.yogadimas.yogadimas_foodmarketbwa.data.local.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.dao.FoodDao
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.dao.RemoteKeysDao
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.entities.FoodEntity
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.entities.RemoteKeys

@Database(entities = [FoodEntity::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class FoodMarketDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: FoodMarketDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): FoodMarketDatabase {
            if (INSTANCE == null) {
                synchronized(FoodMarketDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FoodMarketDatabase::class.java, "db_foodmarket"
                    ).build()
                }
            }
            return INSTANCE as FoodMarketDatabase
        }
    }

}