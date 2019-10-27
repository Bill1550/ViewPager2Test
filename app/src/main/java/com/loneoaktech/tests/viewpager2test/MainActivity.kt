package com.loneoaktech.tests.viewpager2test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.navigation.fragment.NavHostFragment
import com.loneoaktech.utility.extensions.fragmentTransact

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            NavHostFragment.create(R.navigation.main_nav_graph).let { navHost ->
                fragmentTransact {
                    replace(R.id.container, navHost)
                    setPrimaryNavigationFragment(navHost)
                }
            }
        }
    }
}
