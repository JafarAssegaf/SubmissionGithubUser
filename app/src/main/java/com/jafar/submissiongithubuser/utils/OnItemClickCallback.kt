package com.jafar.submissiongithubuser.utils

import com.jafar.submissiongithubuser.data.remote.response.ItemsItem

interface OnItemClickCallback {
    fun onClick(user: ItemsItem)
}