package com.androidkotlin.armoireapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.ui.AppBarConfiguration
import com.androidkotlin.armoireapp.databinding.ActivitynavigatorBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activitynavigator.*
import kotlinx.android.synthetic.main.app_bar_main.*


class NavigatorActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var livingFragment: LivingFragment
    private lateinit var calendarFragment: CalendarFragment
    private lateinit var shopFragment: ShopFragment
    private lateinit var mainlunchFragment: MainLunchFragment
    private lateinit var articlesFragment: ArticlesFragment
    private lateinit var recipesFragment: RecipesFragment
    private lateinit var markFragment: MarkFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitynavigatorBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitynavigator)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerlayout,
            toolbar,
            (R.string.open),
            (R.string.close)
        ){

        }
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerlayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        livingFragment = LivingFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, livingFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }


    override fun onNavigationItemSelected(menuitem: MenuItem): Boolean {
        when (menuitem.itemId) {
            R.id.nav_home -> {
                livingFragment = LivingFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, livingFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.nav_calendar -> {
                calendarFragment = CalendarFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, calendarFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.nav_shop -> {
                shopFragment = ShopFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, shopFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.nav_lunch -> {
                mainlunchFragment = MainLunchFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, mainlunchFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.nav_articles -> {
                articlesFragment = ArticlesFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, articlesFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.nav_recipes -> {
                recipesFragment = RecipesFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, recipesFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.nav_marks -> {
                markFragment = MarkFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, markFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }

        }
        drawerlayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(drawerlayout.isDrawerOpen(GravityCompat.START)){
            drawerlayout.closeDrawer((GravityCompat.START))
        }
        else {
            super.onBackPressed()
        }
    }
}