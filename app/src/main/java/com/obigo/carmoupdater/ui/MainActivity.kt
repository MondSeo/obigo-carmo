package com.obigo.carmoupdater.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.BuildConfig
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.obigo.carmo.ui.StationPagerAdapter
import com.obigo.carmoupdater.R
import com.obigo.carmoupdater.databinding.ActivityMainBinding
import kotlin.math.absoluteValue


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var station : ArrayList<String>

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setImmersiveMode()
        initViews()
        initDatas()
        displayStationsPager(station)


    }

    override fun onStart() {
        super.onStart()
        val appUpdater = AppUpdater(this)
            .setUpdateFrom(UpdateFrom.GITHUB)
            .setGitHubUserAndRepo("mondseo","obigo-carmo-updater")
        appUpdater.start()
    }

    private fun initDatas() {
        station = java.util.ArrayList()
        station.apply {
            station.add("버스 정류장1")
            station.add("버스 정류장2")
            station.add("버스 정류장3")
            station.add("버스 정류장4")
            station.add("버스 정류장5")
            station.add("버스 정류장6")
            station.add("버스 정류장7")
            station.add("버스 정류장8")
        }
    }

    override fun onBackPressed() {
        return
    }

    override fun onStop() {
        super.onStop()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun initViews() {
        binding.viewPager.setPageTransformer { page, position ->
            when {
                position.absoluteValue >= 1F -> {
                    page.alpha = 0F
                }
                position == 0F -> {
                    page.alpha = 1F
                }
                else -> {
                    page.alpha = 1F - position.absoluteValue*2
                }
            }
        }

        binding.currentVersion.text = "현재 버전 : ${BuildConfig.VERSION_NAME}"

    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun setImmersiveMode(){
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        // Configure the behavior of the hidden system bars.
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // Add a listener to update the behavior of the toggle fullscreen button when
        // the system bars are hidden or revealed.
        window.decorView.setOnApplyWindowInsetsListener { view, windowInsets ->
            // You can hide the caption bar even when the other system bars are visible.
            // To account for this, explicitly check the visibility of navigationBars()
            // and statusBars() rather than checking the visibility of systemBars().
            if (windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars())
                || windowInsets.isVisible(WindowInsetsCompat.Type.statusBars())) {
                windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

            } else {
                windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
            }
            view.onApplyWindowInsets(windowInsets)
        }
    }

    private fun displayStationsPager(station:List<String>) {
        val adapter = StationPagerAdapter(station)
        binding.viewPager.adapter = adapter
        binding.viewPager.setCurrentItem(adapter.itemCount / 2, false)
    }


}