package com.yogadimas.yogadimas_foodmarketbwa.data.local.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "food")
data class FoodEntity(
    @PrimaryKey(autoGenerate = false) val id: Long = 1L,

    @ColumnInfo(name = "picturePath") val picturePath: String? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "ingredients") val ingredients: String? = null,
    @ColumnInfo(name = "price") val price: Int? = null,
    @ColumnInfo(name = "rate") val rate: Double? = null,
    @ColumnInfo(name = "types") val types: String = "",

    @ColumnInfo(name = "deleted_at") val deletedAt: Long? = null,

    @ColumnInfo(name = "created_at") val createdAt: Long? = null,
    @ColumnInfo(name = "updated_at") val updatedAt: Long? = null
) : Parcelable


