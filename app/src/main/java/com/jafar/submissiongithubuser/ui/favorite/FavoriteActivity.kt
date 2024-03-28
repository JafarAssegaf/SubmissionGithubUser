package com.jafar.submissiongithubuser.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jafar.submissiongithubuser.data.local.entity.FavoriteEntity
import com.jafar.submissiongithubuser.data.remote.response.ItemsItem
import com.jafar.submissiongithubuser.databinding.ActivityFavoriteBinding
import com.jafar.submissiongithubuser.ui.ViewModelFactory
import com.jafar.submissiongithubuser.ui.adapter.UserAdapter
import com.jafar.submissiongithubuser.ui.detail.DetailActivity
import com.jafar.submissiongithubuser.utils.OnItemClickCallback

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()

        favoriteViewModel.getAllFavorite().observe(this) { users ->
            setUpRecyclerView()
            setUpFavoriteData(users)
            binding.rvFavorites.adapter = adapter
        }
    }

    private fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rvFavorites.layoutManager = linearLayoutManager
        val itemDecoration = DividerItemDecoration(this, linearLayoutManager.orientation)
        binding.rvFavorites.addItemDecoration(itemDecoration)
        binding.rvFavorites.setHasFixedSize(true)
    }

    private fun setUpFavoriteData(users: List<FavoriteEntity>) {
        val arrayUsers = ArrayList<ItemsItem>()
        users.map {
            val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
            arrayUsers.add(item)
        }
        adapter.submitList(arrayUsers)

        adapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onClick(user: ItemsItem) {
                val intentToDetail = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
                startActivity(intentToDetail)
            }
        })
    }
}