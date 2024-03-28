package com.jafar.submissiongithubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jafar.submissiongithubuser.data.local.entity.FavoriteEntity
import com.jafar.submissiongithubuser.data.remote.response.DetailUserResponse
import com.jafar.submissiongithubuser.data.remote.retrofit.ApiConfig
import com.jafar.submissiongithubuser.data.repository.FavoriteRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    private var _detailData = MutableLiveData<DetailUserResponse>()
    val detailData: LiveData<DetailUserResponse> = _detailData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun insertFavoriteUser(user: FavoriteEntity) {
        viewModelScope.launch {
            favoriteRepository.insertFavorite(user)
        }
    }

    fun getUserStateIsFavorite(username: String) = favoriteRepository.getUserStateIsFavorite(username)

    fun deleteUserFromFavoriteByUsername(username: String) {
        viewModelScope.launch {
            favoriteRepository.deleteUserFromFavoriteByUsername(username)
        }
    }

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailData.value = response.body()
                } else {
                    Log.e("DetailViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("DetailViewModel", "onFailure: ${t.message.toString()}")
            }

        })
    }

}