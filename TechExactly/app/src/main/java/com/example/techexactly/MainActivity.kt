package com.example.techexactly

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.techexactly.adapters.TabsAdapter
import com.example.techexactly.databinding.ActivityMainBinding
import com.example.techexactly.fragments.ApplicationsFragment
import com.example.techexactly.fragments.SettingsFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentList: ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewPager()
    }

    private fun setUpViewPager() {
        val tabs = arrayOf("Applications", "Settings")
        fragmentList = ArrayList()
        fragmentList.add(ApplicationsFragment())
        fragmentList.add(SettingsFragment())

        binding.vpTabs.apply {
            isUserInputEnabled = false
            adapter = TabsAdapter(this@MainActivity, fragmentList)
        }

        TabLayoutMediator(binding.tabLayout, binding.vpTabs) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }
}