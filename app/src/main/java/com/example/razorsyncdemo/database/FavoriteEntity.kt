package com.example.razorsyncdemo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.razorsyncdemo.util.Constants.FAVORITE_TABLE

@Entity(tableName = FAVORITE_TABLE)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val favoriteName: String
)
