package com.example.github_user_search.ui.gitSearch;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github_user_search.data.repository.GitHubUserRepository

public class UserSearchViewModelFactory(
    private val repositoryGitHub: GitHubUserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        return UserSearchViewModel(
            repositoryGitHub
        ) as T
    }
}
