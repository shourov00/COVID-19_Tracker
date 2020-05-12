package com.sfazleyrabbi.covid19tracker.ui.main

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sfazleyrabbi.covid19tracker.BaseApplication
import com.sfazleyrabbi.covid19tracker.R
import com.sfazleyrabbi.covid19tracker.fragments.main.global.GlobalFragmentFactory
import com.sfazleyrabbi.covid19tracker.fragments.main.home.HomeFragmentFactory
import com.sfazleyrabbi.covid19tracker.ui.BaseActivity
import com.sfazleyrabbi.covid19tracker.util.BOTTOM_NAV_BACKSTACK_KEY
import com.sfazleyrabbi.covid19tracker.util.BottomNavController
import com.sfazleyrabbi.covid19tracker.util.BottomNavController.OnNavigationGraphChanged
import com.sfazleyrabbi.covid19tracker.util.BottomNavController.OnNavigationReselectedListener
import com.sfazleyrabbi.covid19tracker.util.setUpNavigation
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Named

class MainActivity : BaseActivity(), OnNavigationGraphChanged,
    OnNavigationReselectedListener {

    @Inject
    @Named("InfoFragmentFactory")
    lateinit var infoFragmentFactory: FragmentFactory

    @Inject
    @Named("GlobalFragmentFactory")
    lateinit var globalFragmentFactory: FragmentFactory

    @Inject
    @Named("HomeFragmentFactory")
    lateinit var homeFragmentFactory: FragmentFactory

    private lateinit var bottomNavigationView: BottomNavigationView

    private val bottomNavController by lazy(LazyThreadSafetyMode.NONE) {
        BottomNavController(
            this,
            R.id.main_fragments_container,
            R.id.menu_nav_home,
            this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()
        setupBottomNavigationView(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // save backstack for bottom nav
        outState.putIntArray(BOTTOM_NAV_BACKSTACK_KEY, bottomNavController.navigationBackStack.toIntArray())

    }

    private fun setupBottomNavigationView(savedInstanceState: Bundle?) {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setUpNavigation(bottomNavController, this)

        if(savedInstanceState == null){
            bottomNavController.setupBottomNavigationBackStack(null)
            bottomNavController.onNavigationItemSelected()
        }else{
            (savedInstanceState[BOTTOM_NAV_BACKSTACK_KEY] as IntArray?)?.let { items ->
                val backstack = BottomNavController.BackStack()
                backstack.addAll(items.toTypedArray())
                bottomNavController.setupBottomNavigationBackStack(backstack)
            }
        }
    }

    override fun expandAppBar() {
        findViewById<AppBarLayout>(R.id.app_bar).setExpanded(true)
    }

    override fun onBackPressed() = bottomNavController.onBackPressed()

    private fun setupActionBar(){
        setSupportActionBar(tool_bar)
    }

    override fun inject() {
        (application as BaseApplication).mainComponent().inject(this)
    }

    override fun displayProgressBar(bool: Boolean){
        if(bool){
            progress_bar.visibility = View.VISIBLE
        }
        else{
            progress_bar.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onGraphChange() {
        expandAppBar()
    }

    override fun onReselectNavItem(navController: NavController, fragment: Fragment) {
        // do nothing for now
    }
}
