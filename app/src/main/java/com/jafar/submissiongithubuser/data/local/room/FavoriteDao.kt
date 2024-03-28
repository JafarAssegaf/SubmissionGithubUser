package com.jafar.submissiongithubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jafar.submissiongithubuser.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert
    suspend fun insertFavorite(user: FavoriteEntity)

    @Query("SELECT * FROM favorite WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteEntity>

    @Query("DELETE FROM favorite WHERE username = :username")
    suspend fun deleteUserFromFavoriteByUsername(username: String)

    @Query("SELECT * FROM favorite")
    fun getAllFavorite(): LiveData<List<FavoriteEntity>>

}