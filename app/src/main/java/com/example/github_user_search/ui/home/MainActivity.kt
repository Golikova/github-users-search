package com.example.github_user_search.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.github_user_search.R
import com.example.github_user_search.data.entity.User
import com.example.github_user_search.databinding.ActivityMainBinding
import com.example.github_user_search.databinding.HeaderBinding
import com.example.github_user_search.ui.auth.SignoutActivity
import com.example.github_user_search.ui.drawer.DrawerAdapter
import com.example.github_user_search.ui.gitSearch.GithubUserSearchFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var drawer : DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpDrawer(binding)
        var fragment: Fragment = GithubUserSearchFragment()
        displaySelectedScreen(fragment)

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

    private fun displaySelectedScreen(fragment: Fragment) {
        if (fragment != null) {
            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.commit()
        }
        drawer.closeDrawer(GravityCompat.START)
    }

    override public fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}
