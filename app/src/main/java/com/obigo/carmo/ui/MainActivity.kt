package com.obigo.carmo.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_SETTINGS
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.BuildConfig
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.obigo.carmo.OnClickCountListener
import com.obigo.databinding.ActivityMainBinding
import kotlin.math.absoluteValue


class MainActivity : AppCompatActivity() {

    val viewModel : MainViewModel by viewModels()
    private lateinit var binding : ActivityMainBinding
    private lateinit var station : ArrayList<String>
    private lateinit var appUpdater: AppUpdater

    private val recentVersionObserver : Observer<String> = Observer {
        binding.recentVersion.text = it
    }




    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setImmersiveMode()
        initUpdater()
        initViews()
        initDatas()
        displayStationsPager(station)

    }


    override fun onStart() {
        super.onStart()

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
//
//    override fun onStop() {
//        super.onStop()
//        startActivity(Intent(this, MainActivity::class.java))
//    }

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

        viewModel.appUpdaterUtils.start()
        viewModel.recentVersion.observe(this,recentVersionObserver)
        appUpdater.start()

        binding.hiddenSettingView.setOnClickListener(object : OnClickCountListener(){
            override fun onCountClick(view: View) {
                val intent = Intent(ACTION_SETTINGS)
                startActivity(intent)
            }

        })


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
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            view.onApplyWindowInsets(windowInsets)
        }
    }

    private fun displayStationsPager(station:List<String>) {
        val adapter = StationPagerAdapter(station)
        binding.viewPager.adapter = adapter
        binding.viewPager.setCurrentItem(adapter.itemCount / 2, false)
    }

    private fun initUpdater() {
        appUpdater = AppUpdater(this@MainActivity)
            .setUpdateFrom(UpdateFrom.GITHUB)
            .setButtonUpdateClickListener { dialog, which ->

                val intent = Intent(Intent.ACTION_MAIN)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.component = viewModel.componentName
                intent.putExtra("url",viewModel.url)
                startActivity(intent)
//                moveTaskToBack(true);						// 태스크를 백그라운드로 이동
//                finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
//                android.os.Process.killProcess(android.os.Process.myPid());	// 앱 프로세스

            }
            .setGitHubUserAndRepo("MondSeo","obigo-carmo")
    }

}