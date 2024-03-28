package com.jafar.submissiongithubuser.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jafar.submissiongithubuser.ui.follow.FollowFragment

class FollowPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username = ""
    override fun createFragment(position: Int): Fragment {
        val followFragment = FollowFragment()

        followFragment.arguments = Bundle().apply {
            putInt(FollowFragment.ARG_POSITION, position + 1)
            putString(FollowFragment.ARG_USERNAME, username)
        }
        return followFragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}