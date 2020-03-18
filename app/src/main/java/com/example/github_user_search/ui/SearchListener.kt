package com.example.github_user_search.ui

import com.example.github_user_search.data.entity.User

interface SearchListener {
    fun onStarted();
    fun onSuccess();
    fun onFailure(message : String);

}