package com.jafar.submissiongithubuser.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jafar.submissiongithubuser.data.repository.FavoriteRepository
import com.jafar.submissiongithubuser.di.Injection
import com.jafar.submissiongithubuser.ui.detail.DetailViewModel
import com.jafar.submissiongithubuser.ui.favorite.FavoriteViewModel
import com.jafar.submissiongithubuser.ui.main.MainViewModel
import com.jafar.submissiongithubuser.utils.SettingPreferences
import com.jafar.submissiongithubuser.utils.dataStore

class ViewModelFactory private constructor(
    private val favoriteRepository: FavoriteRepository,
    private val pref: SettingPreferences,
) : ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(favoriteRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favoriteRepository) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object {

        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(context), SettingPreferences.getInstance(context.dataStore))
            }
        }
    }
}