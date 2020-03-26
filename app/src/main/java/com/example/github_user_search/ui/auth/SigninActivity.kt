package com.example.github_user_search.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github_user_search.R
import com.example.github_user_search.data.db.AppDB
import com.example.github_user_search.data.network.GoogleApi
import com.example.github_user_search.data.repository.UserRepository
import com.example.github_user_search.databinding.ActivitySigninBinding
import com.example.github_user_search.ui.home.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn

class SigninActivity : AppCompatActivity(), AuthListener {

    private val RC_SIGN_IN = 9001
    lateinit var viewModel : AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = GoogleApi.buildGoogleApiClient(this)
        val db = AppDB(this)
        val repository = UserRepository(api, db)

        val factory = AuthViewModelFactory(repository)

        val binding : ActivitySigninBinding = DataBindingUtil.setContentView(this, R.layout.activity_signin)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        binding.viewmodel = viewModel
        viewModel.authListener = this
        viewModel.mGoogleSignInClient = api

        viewModel.getLoggedInUser().observe(this, Observer {
            user ->
            if (user != null) {
                Intent(this, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    val userArray = arrayListOf(user.googleId, user.googleFirstName, user.googleLastName, user.googleEmail, user.googleProfilePicUrl, user.googleIdToken)
                    it.putStringArrayListExtra("LoggedInUser", userArray)
                    startActivity(it)
                }
            }
        })
    }

    override fun startActivity(signInIntent: Intent) {
        startActivityForResult(
            signInIntent, RC_SIGN_IN
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
                viewModel.handleSignInResult(task)
        }
    }


    override fun onStarted() {

    }

    override fun onSuccess() {

    }

    override fun onFailure(message: String) {

    }

}