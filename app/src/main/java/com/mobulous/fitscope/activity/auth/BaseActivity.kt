package com.mobulous.fitscope.activity.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.mobulous.BaseAc
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.ActivityBaseBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.PrefUtils
import com.mobulous.listner.SubcriptionFromSideMenuListnr


class BaseActivity : BaseAc<ActivityBaseBinding>(), SubcriptionFromSideMenuListnr {
    private lateinit var navController: NavController
    private lateinit var navGraph: NavGraph

    override fun onSubcriptionfromSideMenu() {
        if (!Constants.isSignOutFromInside) {
            finish()
        } else {
            this.finishAffinity()
        }

    }

    override fun getViewBinding(): ActivityBaseBinding = ActivityBaseBinding.inflate(layoutInflater)

    override fun listnr() {

    }

    override fun initViews() {
        if (intent.getStringExtra(Constants.From) != null) {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.baseFragment) as NavHostFragment
            val graphInflater = navHostFragment.navController.navInflater
            navGraph = graphInflater.inflate(R.navigation.auth2)
            navController = navHostFragment.navController
            navController.graph = navGraph
            return
        } else if (intent.getStringExtra(Constants.Data) != null && intent.getStringExtra(Constants.Data) != "login") {
            try {
                val navHostFragment = supportFragmentManager
                    .findFragmentById(R.id.baseFragment) as NavHostFragment
                val graphInflater = navHostFragment.navController.navInflater
                navGraph = graphInflater.inflate(R.navigation.auth)
                navController = navHostFragment.navController
                navGraph.startDestination = R.id.subscriptionFragment
                navController.graph = navGraph

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return
        } else if (intent.getStringExtra(Constants.Data) != null && intent.getStringExtra(Constants.Data) == "login") {
            try {
                PrefUtils.with(this).clear()
                val navHostFragment = supportFragmentManager
                    .findFragmentById(R.id.baseFragment) as NavHostFragment
                val graphInflater = navHostFragment.navController.navInflater
                navGraph = graphInflater.inflate(R.navigation.auth)
                navController = navHostFragment.navController
                navGraph.startDestination = R.id.loginFragment
                navController.graph = navGraph

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return
        }
    }

    override fun observers() {

    }


}