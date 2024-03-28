package com.jafar.submissiongithubuser.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.jafar.submissiongithubuser.R
import com.jafar.submissiongithubuser.data.local.entity.FavoriteEntity
import com.jafar.submissiongithubuser.data.remote.response.DetailUserResponse
import com.jafar.submissiongithubuser.databinding.ActivityDetailBinding
import com.jafar.submissiongithubuser.ui.ViewModelFactory
import com.jafar.submissiongithubuser.ui.adapter.FollowPagerAdapter

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val detailViewModel: DetailViewModel by viewModels {
            factory
        }

        val followPagerAdapter = FollowPagerAdapter(this)

        val username = intent.getStringExtra(EXTRA_USERNAME)

        if (username != null) {
            detailViewModel.getDetailUser(username)
            followPagerAdapter.username = username
        } else {
            Toast.makeText(this, "Username null", Toast.LENGTH_SHORT).show()
        }

        detailViewModel.getUserStateIsFavorite(username as String).observe(this) { favoriteState ->
            if (favoriteState != null) {
                binding.fabFavorite.setImageResource(R.drawable.heart)
                isFavorite = true
            } else {
                binding.fabFavorite.setImageResource(R.drawable.heart_outline)
                isFavorite = false
            }
        }

        val favorite = FavoriteEntity()

        detailViewModel.detailData.observe(this) { dataUser ->
            setUpDetailData(dataUser)
            favorite.username = dataUser.login.toString()
            favorite.avatarUrl = dataUser.avatarUrl.toString()
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.viewPagerFollowingFollowers.adapter = followPagerAdapter
        TabLayoutMediator(
            binding.tabsFollowersFollowing,
            binding.viewPagerFollowingFollowers) { tabs, position ->
            tabs.text = TAB_TITLES[position]
        }.attach()

        binding.fabFavorite.setOnClickListener {
            if (isFavorite) {
                detailViewModel.deleteUserFromFavoriteByUsername(username)
            } else {
                detailViewModel.insertFavoriteUser(favorite)
            }
        }
    }

    private fun setUpDetailData(data: DetailUserResponse) {
        Glide.with(this)
            .load(data.avatarUrl)
            .centerCrop()
            .into(binding.ivDetailAvatar)
        binding.tvName.text = data.name.toString()
        binding.tvUsername.text = data.login.toString()
        val textFollowers = "Followers ${data.followers}"
        binding.tvTotalFollowers.text = textFollowers
        val textFollowing = "Following ${data.following}"
        binding.tvTotalFollowing.text = textFollowing
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        private val TAB_TITLES = arrayListOf("Followers", "Following")
    }
}