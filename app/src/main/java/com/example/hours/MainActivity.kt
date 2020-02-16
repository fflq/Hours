package com.example.hours

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav.*

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // toolbar's button
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_home_black_24dp)
        }


        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_item_home -> drawerlayout.closeDrawers()
                R.id.nav_item_person -> startActivity(Intent(this, PersonActivity::class.java))
                R.id.nav_item_settings -> startActivity(Intent(this, PersonActivity::class.java))
                R.id.nav_item_about -> startActivity(Intent(this, PersonActivity::class.java))
            }

            true
        }

        navController = findNavController(R.id.navhost_home_fragment)
        //val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(bottom_nav_view.menu).build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottom_nav_view.setupWithNavController(navController)

    }

}
