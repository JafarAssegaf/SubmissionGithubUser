package com.jafar.submissiongithubuser.ui.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jafar.submissiongithubuser.data.remote.response.ItemsItem
import com.jafar.submissiongithubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private var _dataFollowers = MutableLiveData<List<ItemsItem>>()
    val dataFollowers: LiveData<List<ItemsItem>> = _dataFollowers

    private var _dataFollowing = MutableLiveData<List<ItemsItem>>()
    val dataFollowing: LiveData<List<ItemsItem>> = _dataFollowing

    // loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _dataFollowers.value = response.body()
                    Log.d("FollowViewModel", response.body().toString())
                } else {
                    Log.e("FollowViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e("FollowViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _dataFollowing.value = response.body()
                } else {
                    Log.e("FollowViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e("FollowViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }

}