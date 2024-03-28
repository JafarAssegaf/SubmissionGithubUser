package com.jafar.submissiongithubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jafar.submissiongithubuser.data.remote.response.GithubResponse
import com.jafar.submissiongithubuser.data.remote.response.ItemsItem
import com.jafar.submissiongithubuser.data.remote.retrofit.ApiConfig
import com.jafar.submissiongithubuser.utils.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {

    // MutableLiveData Github user
    private val _githubUser = MutableLiveData<List<ItemsItem?>?>()
    val githubUser: LiveData<List<ItemsItem?>?> = _githubUser

    // loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getUserByUsername("Jafar")
    }

    fun getUserByUsername(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsersByUsername(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _githubUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSettings(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSettings(isDarkModeActive)
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}