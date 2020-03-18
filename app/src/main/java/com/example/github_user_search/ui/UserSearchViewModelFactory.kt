package com.example.github_user_search.ui;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github_user_search.data.repository.UserRepository

public class UserSearchViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        return UserSearchViewModel(repository) as T
    }
}
