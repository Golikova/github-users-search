package com.example.github_user_search.data.network.responses

import com.example.github_user_search.data.entity.User

data class GitHubResponse (
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<User>
)