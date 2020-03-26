package com.example.github_user_search.ui.auth;

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.github_user_search.data.db.AppDB
import com.example.github_user_search.data.network.GoogleApi
import com.example.github_user_search.data.repository.UserRepository

 class SignoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = GoogleApi.buildGoogleApiClient(this)
        val db = AppDB(this)
        val repository = UserRepository(api, db)
        repository.removeUser()

        val intent = Intent(this, SigninActivity::class.java)
        startActivity(intent)

    }
}
