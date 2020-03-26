package com.example.github_user_search.ui.home

interface SearchListener {
    fun onStarted();
    fun onSuccess();
    fun onFailure(message : String);

}