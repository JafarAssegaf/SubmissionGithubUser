package com.jafar.submissiongithubuser.di

import android.content.Context
import com.jafar.submissiongithubuser.data.local.room.FavoriteDatabase
import com.jafar.submissiongithubuser.data.repository.FavoriteRepository

object Injection {

    fun provideRepository(context: Context): FavoriteRepository {
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.favoriteDao()
        return FavoriteRepository.getInstance(dao)
    }
}