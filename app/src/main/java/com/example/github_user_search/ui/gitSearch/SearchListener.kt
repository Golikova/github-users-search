package com.example.github_user_search.ui.gitSearch

interface SearchListener {
    fun onStarted();
    fun onSuccess();
    fun onFailure(message : String);

}