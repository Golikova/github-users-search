package com.example.github_user_search.data.repository

import android.content.Intent
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.github_user_search.data.db.AppDB
import com.example.github_user_search.data.entity.User
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class UserRepository(
    private val api: GoogleSignInClient,
    private val db: AppDB
) {

    fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun removeUser() = db.getUserDao().remove()

    fun getUser()  = db.getUserDao().getUser()

}