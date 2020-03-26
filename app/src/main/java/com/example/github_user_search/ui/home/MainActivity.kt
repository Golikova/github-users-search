package com.example.github_user_search.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_user_search.R
import com.example.github_user_search.data.entity.GithubUser
import com.example.github_user_search.data.entity.User
import com.example.github_user_search.data.network.GitHubApi
import com.example.github_user_search.data.repository.GitHubUserRepository
import com.example.github_user_search.databinding.ActivityMainBinding
import com.example.github_user_search.databinding.HeaderBinding
import com.example.github_user_search.util.hide
import com.example.github_user_search.util.show
import com.example.github_user_search.util.toast
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.widget.Toolbar
import com.example.github_user_search.ui.auth.SignoutActivity
import com.example.github_user_search.ui.drawer.DrawerAdapter


class MainActivity : AppCompatActivity() ,
    SearchListener,
    RecyclerViewAdapter.OnItemClickListener {

    lateinit var drawer : DrawerLayout

    private val recyclerViewAdapter =
        RecyclerViewAdapter(
            listOf(),
            this
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = GitHubApi()
        val gitHubUserRepository = GitHubUserRepository(api)
        val factory = UserSearchViewModelFactory(gitHubUserRepository)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProvider(this, factory).get(UserSearchViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.searchListener = this
        binding.recyclerUsers.layoutManager = LinearLayoutManager(this);
        binding.recyclerUsers.adapter = recyclerViewAdapter

        viewModel.users?.observe(this,
            Observer<List<GithubUser>> { it?.let{ recyclerViewAdapter.replaceData(it)} })

        setUpDrawer(binding)

    }

    private fun setUpDrawer(binding: ActivityMainBinding) {
        val userDataArray = intent.getStringArrayListExtra("LoggedInUser");
        val user = User(
            userDataArray[0],
            userDataArray[1],
            userDataArray[2],
            userDataArray[3],
            userDataArray[4],
            userDataArray[5]
        )
        val bindingHeader: HeaderBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.header, binding.navView, false)
        binding.navView.addHeaderView(bindingHeader.root)
        val drawerAdapter =
            DrawerAdapter(user)
        bindingHeader.user = drawerAdapter

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.nav_drawer_open,
            R.string.nav_drawer_close
        )
        drawer.addDrawerListener(toggle);
        toggle.syncState()


        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_logout -> {
                    Log.d("nav", "logout pressed")

                    val intent = Intent(this, SignoutActivity::class.java)
                    startActivity(intent)
                    if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                        drawer_layout.closeDrawer(GravityCompat.START)
                    }
                    true
                } else -> {
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                }
                false
            }
            }
        }
    }

    override public fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
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
