package com.example.hours

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav.*
import android.view.Gravity as Gravity1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav)

        setSupportActionBar(toolbar)
        // hide by fragment
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // toolbar's button
            setDisplayShowHomeEnabled(true)
        }

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_item_home -> drawerlayout.closeDrawers()
            }

            true
        }

        val navController = findNavController(R.id.nav_host_fragment)
        //val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(bottom_nav_view.menu).build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottom_nav_view.setupWithNavController(navController)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // toolbar's button
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // toolbar's button for opening drawer
            android.R.id.home -> drawerlayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    // override toolbar menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }
}
