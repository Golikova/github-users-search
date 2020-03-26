package com.example.github_user_search.data.repository;

import com.example.github_user_search.data.db.AppDB
import com.example.github_user_search.data.entity.GithubUser
import com.example.github_user_search.data.entity.User
import com.example.github_user_search.data.network.GitHubApi
import com.example.github_user_search.data.network.responses.GitHubResponse
import io.reactivex.Observable

class GitHubUserRepository (
    private val api: GitHubApi
) {

    fun getGithubUsers(login: String): Observable<GitHubResponse> {
        return GitHubApi.invoke().search(login)
    }



}
