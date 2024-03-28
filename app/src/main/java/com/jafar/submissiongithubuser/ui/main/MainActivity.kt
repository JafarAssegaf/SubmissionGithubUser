package com.jafar.submissiongithubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jafar.submissiongithubuser.data.remote.response.ItemsItem
import com.jafar.submissiongithubuser.databinding.ActivityMainBinding
import com.jafar.submissiongithubuser.ui.SettingActivity
import com.jafar.submissiongithubuser.ui.ViewModelFactory
import com.jafar.submissiongithubuser.ui.adapter.UserAdapter
import com.jafar.submissiongithubuser.ui.detail.DetailActivity
import com.jafar.submissiongithubuser.ui.favorite.FavoriteActivity
import com.jafar.submissiongithubuser.utils.OnItemClickCallback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        mainViewModel.githubUser.observe(this) { listUser ->
            if (listUser != null) {
                setUpUserData(listUser)
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with (binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.getUserByUsername(searchBar.text.toString())
                    false
                }
        }
    }

    private fun setUpUserData(user: List<ItemsItem?>) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding.rvUsers.adapter = adapter

        adapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onClick(user: ItemsItem) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.EXTRA_USERNAME, user.login.toString())
                startActivity(intentToDetail)
            }
        })
    }

    fun favoriteMenuClicked(item: MenuItem) {
        val intentToFavorite = Intent(this, FavoriteActivity::class.java)
        startActivity(intentToFavorite)
    }

    fun settingMenuClicked(item : MenuItem) {
        val intentToSetting = Intent(this, SettingActivity::class.java)
        startActivity(intentToSetting)
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}