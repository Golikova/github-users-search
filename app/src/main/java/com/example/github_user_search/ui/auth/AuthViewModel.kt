package com.example.github_user_search.ui.auth

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.github_user_search.data.entity.User
import com.example.github_user_search.data.repository.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class AuthViewModel (
    private val repository: UserRepository
): ViewModel() {


    var mGoogleSignInClient: GoogleSignInClient? = null
    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()

    fun onSigninClickButton(view: View) {

        println("BTN clicked")

        val signInIntent = mGoogleSignInClient?.signInIntent
        authListener?.startActivity(
            signInIntent!!
        )

    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        Log.d("SIGNIN", "Handled result")
        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )
            // Signed in successfully
            val googleId = account?.id ?: ""
            Log.i("Google ID",googleId)

            val googleFirstName = account?.givenName ?: ""
            Log.i("Google First Name", googleFirstName)

            val googleLastName = account?.familyName ?: ""
            Log.i("Google Last Name", googleLastName)

            val googleEmail = account?.email ?: ""
            Log.i("Google Email", googleEmail)

            val googleProfilePicURL = account?.photoUrl.toString()
            Log.i("Google Profile Pic URL", googleProfilePicURL)

            val googleIdToken = account?.idToken ?: ""
            Log.i("Google ID Token", googleIdToken)

            var user = User(googleId, googleFirstName, googleLastName, googleEmail, googleProfilePicURL, googleIdToken)
            Observable.just(repository)
                .subscribeOn(Schedulers.io())
                .subscribe { repository -> repository.saveUser(user) }


        } catch (e: ApiException) {
            // Sign in was unsuccessful
            Log.e(
                "failed code=", e.statusCode.toString()
            )
        }
    }

}