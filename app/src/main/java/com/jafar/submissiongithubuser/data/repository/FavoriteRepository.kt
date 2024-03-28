package com.jafar.submissiongithubuser.data.repository

import androidx.lifecycle.LiveData
import com.jafar.submissiongithubuser.data.local.entity.FavoriteEntity
import com.jafar.submissiongithubuser.data.local.room.FavoriteDao

class FavoriteRepository private constructor(
    private val favoriteDao: FavoriteDao
) {

    suspend fun insertFavorite(user: FavoriteEntity) {
        favoriteDao.insertFavorite(user)
    }

    fun getUserStateIsFavorite(username: String): LiveData<FavoriteEntity> {
        return favoriteDao.getFavoriteUserByUsername(username)
    }

    suspend fun deleteUserFromFavoriteByUsername(username: String) {
        favoriteDao.deleteUserFromFavoriteByUsername(username)
    }

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> {
        return favoriteDao.getAllFavorite()
    }

    companion object {
        @Volatile
        private var INSTANCE: FavoriteRepository? = null

        fun getInstance(favoriteDao: FavoriteDao): FavoriteRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteRepository(
                    favoriteDao
                )
            }
        }
    }

}