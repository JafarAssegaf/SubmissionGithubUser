package com.jafar.submissiongithubuser.ui.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jafar.submissiongithubuser.data.remote.response.ItemsItem
import com.jafar.submissiongithubuser.databinding.FragmentFollowBinding
import com.jafar.submissiongithubuser.ui.adapter.UserAdapter

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private val followViewModel by viewModels<FollowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root as View
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(ARG_POSITION)
        val username = arguments?.getString(ARG_USERNAME)

        setupRecyclerView()

        if (position == 1) {
            username?.let { followViewModel.getFollowers(it) }
        } else if (position == 2) {
            username?.let { followViewModel.getFollowing(it) }
        }

        followViewModel.dataFollowers.observe(viewLifecycleOwner) { dataFollower ->
            setUpUserData(dataFollower)
        }

        followViewModel.dataFollowing.observe(viewLifecycleOwner) { dataFollowing ->
            setUpUserData(dataFollowing)
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun setUpUserData(user: List<ItemsItem?>) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding?.rvFollowUsers?.adapter = adapter
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvFollowUsers?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding?.rvFollowUsers?.addItemDecoration(itemDecoration)
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }
}