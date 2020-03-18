package com.example.github_user_search.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_user_search.R
import com.example.github_user_search.data.entity.User
import com.example.github_user_search.data.network.GitHubApi
import com.example.github_user_search.data.repository.UserRepository
import com.example.github_user_search.databinding.ActivityMainBinding
import com.example.github_user_search.util.hide
import com.example.github_user_search.util.show
import com.example.github_user_search.util.toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , SearchListener, RecyclerViewAdapter.OnItemClickListener {

    private val recyclerViewAdapter = RecyclerViewAdapter(listOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = GitHubApi()
        val repository = UserRepository(api)
        val factory = UserSearchViewModelFactory(repository)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this, factory).get(UserSearchViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.searchListener = this

        binding.recyclerUsers.layoutManager = LinearLayoutManager(this);

        binding.recyclerUsers.adapter = recyclerViewAdapter
        viewModel.users?.observe(this,
            Observer<List<User>> { it?.let{ recyclerViewAdapter.replaceData(it)} })

    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess() {
        progress_bar.hide()
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        toast(message)
    }

    override fun onItemClick(position: Int) {
        toast("Здесь пока не функционала...")
    }


}
