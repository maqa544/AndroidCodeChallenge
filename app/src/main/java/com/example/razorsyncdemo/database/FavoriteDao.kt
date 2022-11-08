package com.example.razorsyncdemo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface FavoriteDao {

    @Query("SELECT * FROM FAVORITE_TABLE")
    fun getAllFavorite(): LiveData<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM FAVORITE_TABLE WHERE favoriteName = :name")
    suspend fun deleteFavoriteByName(name: String)
}
