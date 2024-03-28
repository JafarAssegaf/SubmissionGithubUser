package com.jafar.submissiongithubuser.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jafar.submissiongithubuser.data.local.entity.FavoriteEntity
import com.jafar.submissiongithubuser.data.repository.FavoriteRepository

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> = repository.getAllFavorite()

}