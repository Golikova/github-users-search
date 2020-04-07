package com.example.github_user_search.ui.gitSearch;

import android.text.Editable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_user_search.data.entity.GithubUser
import com.example.github_user_search.data.repository.GitHubUserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.*


open class UserSearchViewModel(
        private val repositoryGitHub: GitHubUserRepository
) : ViewModel() {


        var login : String? = null
        var searchListener: SearchListener? = null
        var users = MutableLiveData<List<GithubUser>>()

    fun refresh(s : String) {

           if (s.isNullOrEmpty()){
               users.postValue(listOfNotNull())
               return
            }

            try {
                val observableGitHubResponse = repositoryGitHub.getGithubUsers(s.toString()!!)

                observableGitHubResponse
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe ({
                            result ->
                        users.value = result.items
                        Log.d("USERS", users.value.toString())
                    }, { error ->
                        error.printStackTrace()
                    })


            } catch (e: IOException){
            }

        }

    fun afterTextChanged(s: Editable?) {

        var timer = Timer()
        val delay: Long = 100
        Log.d("LOGIN", s.toString())
        timer.cancel()
        timer = Timer()
        timer.schedule(
            object : TimerTask() {
                override fun run() {
                    refresh(s.toString())

                }
            },
            delay
        )
    }

}
