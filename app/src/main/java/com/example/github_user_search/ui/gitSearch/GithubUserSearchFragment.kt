package com.example.github_user_search.ui.gitSearch;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_user_search.R
import com.example.github_user_search.data.entity.GithubUser
import com.example.github_user_search.data.network.GitHubApi
import com.example.github_user_search.data.repository.GitHubUserRepository
import com.example.github_user_search.databinding.FragmentMainBinding


class GithubUserSearchFragment : Fragment(),
    SearchListener,
    RecyclerViewAdapter.OnItemClickListener {

    private val recyclerViewAdapter =
        RecyclerViewAdapter(
            listOf(),
            this
        )

    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )
            : View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )
        val view: View = binding.getRoot()

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val api = GitHubApi()
        val gitHubUserRepository = GitHubUserRepository(api)
        val factory =
            UserSearchViewModelFactory(
                gitHubUserRepository
            )

        val viewModel = ViewModelProvider(this, factory).get(UserSearchViewModel::class.java)

        binding.viewmodel = viewModel
        viewModel.searchListener = this
        binding.recyclerUsers.layoutManager = LinearLayoutManager(this.activity);
        binding.recyclerUsers.adapter = recyclerViewAdapter

        viewModel.users?.observe(viewLifecycleOwner,
            Observer<List<GithubUser>> { it?.let { recyclerViewAdapter.replaceData(it) } })

    }

    override fun onStarted() {
    }

    override fun onSuccess() {
    }

    override fun onFailure(message: String) {
    }

    override fun onItemClick(position: Int) {
        println("Item clicked")
    }

}
