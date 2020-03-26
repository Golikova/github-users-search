package com.example.github_user_search.ui.auth;

import android.content.Intent

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
    fun startActivity(intent: Intent)
}