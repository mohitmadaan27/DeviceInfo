package com.deviceinfosample.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.deviceinfosample.R
import androidx.viewpager.widget.ViewPager
import com.deviceinfosample.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val viewPager = findViewById<View>(R.id.viewpager) as ViewPager
        viewPager.offscreenPageLimit = 0
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addTitle("Location")
        adapter.addTitle("App")
        adapter.addTitle("Ads")
        adapter.addTitle("Battery")
        adapter.addTitle("Device")
        adapter.addTitle("Memory")
        adapter.addTitle("Network")
        adapter.addTitle("Installed Apps")
        adapter.addTitle("Contacts")
        viewPager.adapter = adapter
        val tabLayout = findViewById<View>(R.id.tab_layout) as TabLayout
        tabLayout.setupWithViewPager(viewPager)
    }
}