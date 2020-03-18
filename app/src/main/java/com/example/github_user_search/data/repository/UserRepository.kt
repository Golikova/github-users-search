package com.example.github_user_search.data.repository;

import android.util.Log
import com.example.github_user_search.data.network.GitHubApi
import com.example.github_user_search.data.network.responses.GitHubResponse
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UserRepository (
    private val api: GitHubApi
) {

    fun getUsers(login: String): Observable<GitHubResponse> {
        return GitHubApi.invoke().search(login)
    }

}
