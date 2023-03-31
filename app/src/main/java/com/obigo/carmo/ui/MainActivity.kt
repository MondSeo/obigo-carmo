package com.obigo.carmo.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_SETTINGS
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.obigo.carmo.*
import com.obigo.carmo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    /**
     * ViewModel
     */
    private val viewModel: MainViewModel by viewModels()

    /**
     * 바인딩
     */

    private lateinit var binding: ActivityMainBinding
    /**
     * 정류장을 나타낼 ArrayList
     */

    private lateinit var station: ArrayList<String>

    /**
     * 애니메이션 항목 Array
     */
    private lateinit var animation : Array<String>

    /**
     * Array 객체를 연결해줄 드랍다운 Adapter
     */
    private lateinit var animationDropDownAdapter : ArrayAdapter<String>

    /**
     * 깃허브의 URL을 가져와줄 AppUpdater
     */
    private lateinit var appUpdater: AppUpdater

    /**
     * 현재 드랍다운에 클릭된 애니메이션
     */
    private lateinit var selectedAniamtion : String

    /**
     * 애니메이션 함수 클래스
     */
    private val animationFunctions = AnimationFunctions(this@MainActivity)

    /**
     * 최근 버전 옵저버
     */
    private val recentVersionObserver: Observer<String> = Observer {
        binding.recentVersion.text = it
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setImmersiveMode()
        initUpdateCheck()
        initDatas()
        initViews()
    }

    /**
     * 데이터 세팅
     */
    private fun initDatas() {
        station = ArrayList()
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

        animation  = resources.getStringArray(R.array.animations)
        animationDropDownAdapter = ArrayAdapter(this, R.layout.item_spinner_animation,animation)
        selectedAniamtion = animation[0]

        binding.spinnerAnimations.adapter = animationDropDownAdapter
    }


    /**
     * 런처 앱이므로 뒤로 가기 막음
     */
    override fun onBackPressed() {
        return
    }

    /**
     * 항목들 활성화
     */
    private fun initViews() {
        binding.currentVersion.text = "현재 버전 : ${BuildConfig.VERSION_NAME}"

        viewModel.appUpdaterUtils.start()
        viewModel.recentVersion.observe(this, recentVersionObserver)
        appUpdater.start()

        binding.hiddenSettingView.setOnClickListener(object : OnHiddenClickCountListener() {
            override fun onCountClick(view: View) {
                val intent = Intent(ACTION_SETTINGS)
                startActivity(intent)
            }
        })

        /**
         * private fun 하나 만들고 position 별로 인자 커스텀해서 애니메이션 뿌려주는 함수 생성
         */
        binding.spinnerAnimations.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ){
                selectedAniamtion = animation[position]
                Log.d(TAG,selectedAniamtion)
//                animationFunctions.startSampleAnimation()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


    }

    /**
     * 몰입 모드
     */
    @RequiresApi(Build.VERSION_CODES.R)
    private fun setImmersiveMode() {
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

    /**
     * 업데이트 체크
     */
    private fun initUpdateCheck() {
        appUpdater = AppUpdater(this@MainActivity)
            .setUpdateFrom(UpdateFrom.GITHUB)
            .setButtonUpdateClickListener { dialog, which ->
                val intent = Intent(Intent.ACTION_MAIN)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.component = viewModel.componentName
                intent.putExtra("url",viewModel.url)
                intent.putExtra("version",viewModel.latestVersion)
                startActivity(intent)
                moveTaskToBack(true);						// 태스크를 백그라운드로 이동
                finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
                android.os.Process.killProcess(android.os.Process.myPid());	// 앱 프로세스 종료
            }
            .setGitHubUserAndRepo("MondSeo", "obigo-carmo")

        val connection = NetworkConnection(applicationContext)
        connection.observe(this, Observer { isConnected ->
            if (isConnected)
            {
                viewModel.appUpdaterUtils.start()
                viewModel.recentVersion.observe(this, recentVersionObserver)
                appUpdater.start()
            } else
            {
                appUpdater.stop()
            }
        })
    }
}