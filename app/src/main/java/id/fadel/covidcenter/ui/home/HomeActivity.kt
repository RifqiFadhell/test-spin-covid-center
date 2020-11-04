package id.fadel.covidcenter.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import id.fadel.covidcenter.R
import id.fadel.covidcenter.preference.DataSaved
import id.fadel.covidcenter.ui.home.dashboard.DashboardFragment
import id.fadel.covidcenter.ui.home.profile.ProfileFragment
import id.fadel.covidcenter.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity() {

    private var dashboardFragment = DashboardFragment()
    private var profileFragment = ProfileFragment()
    private var fragmentManager = supportFragmentManager
    private var fragmentActive: Fragment = dashboardFragment
    private var dataSaved: DataSaved? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        init()
        initListener()
    }

    private fun init() {
        dataSaved = DataSaved(this)

        if (dataSaved?.getLogin(this).isNullOrEmpty()) {
            goToSplash()
        } else {
            initFragments()
        }
    }

    private fun initListener() {

        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {

                R.id.homeDashboard -> {
                    fragmentManager.beginTransaction().hide(fragmentActive).show(dashboardFragment)
                            .commit()
                    fragmentActive = dashboardFragment
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.homeAccount -> {
                    fragmentManager.beginTransaction().hide(fragmentActive).show(profileFragment).commit()
                    fragmentActive = profileFragment
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun goToSplash() {
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initFragments() {
        fragmentManager.beginTransaction().add(R.id.frameLayout, dashboardFragment).commit()
        fragmentManager.beginTransaction().add(R.id.frameLayout, profileFragment).hide(profileFragment).commit()
    }
}