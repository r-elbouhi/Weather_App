package com.rami.weatherapp.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.rami.weatherapp.R
import com.rami.weatherapp.databinding.ActivityHomeBinding
import com.rami.weatherapp.ui.base.BaseActivity
import com.rami.weatherapp.utils.GPS_REQUEST_CODE
import com.rami.weatherapp.utils.ScopeEnum
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val scope: Scope =
        getKoin().getOrCreateScope(ScopeEnum.Home.scope, named(ScopeEnum.Home.scope))

    override fun getLayoutId() = R.layout.activity_home

    override fun setup(savedInstanceState: Bundle?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST_CODE) {
                val navHostFragment = supportFragmentManager.fragments.first() as? NavHostFragment
                if (navHostFragment != null) {
                    val childFragments = navHostFragment.childFragmentManager.fragments
                    childFragments.forEach { fragment ->
                        if (fragment is HomeFragment) {
                            fragment.getCurrentLocation()
                        }
                    }
                }
            }
        }
    }
}