package com.obigo.carmo.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.javiersantos.appupdater.AppUpdaterUtils
import com.github.javiersantos.appupdater.enums.AppUpdaterError
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.github.javiersantos.appupdater.objects.Update
import com.obigo.carmo.AnimationFunctions
import com.obigo.carmo.R
import kotlinx.coroutines.delay
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer

class MainViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")

    /**
     * 컨텍스트
     */
    private val context = getApplication<Application>().applicationContext

    /**
     * 최신 버전 LiveData
     */
    private val _recentVersion = MutableLiveData<String>()
    val recentVersion: LiveData<String> get() = _recentVersion

    /**
     * 현재 정류장 LiveData
     */
    private val _currentStation = MutableLiveData<String>()
    val currentStation : LiveData<String> get() = _currentStation

    /**
     * 정류장
     */
    private val station = ArrayList<String>()

    /**
     * 애니메이션 함수 클래스
     */
    private val animationFunctions = AnimationFunctions(context)

    /**
     * 타이머 가동중인지 체크
     */
    private var isTimerRunning : Boolean = false

    /**
     * 현재 선택된 애니메이션
     */
    var selectedAnimation : Int = 0

    /**
     * url 전역객체 초기화
     */
    var url: String = ""

    /**
     * 최신 버전 전역객체 초기화
     */
    var latestVersion : String = ""

    /**
     * 이동할 앱의 패키지 명
     */
    val componentName: ComponentName =
        ComponentName("com.obigo.carmoupdater", "com.obigo.carmoupdater.MainActivity")



    /**
     * AppUpdater 라이브러리의 유틸 클래스
     */
    val appUpdaterUtils: AppUpdaterUtils = AppUpdaterUtils(context)
        .setUpdateFrom(UpdateFrom.GITHUB)
        .setGitHubUserAndRepo("mondseo", "obigo-carmo")
        .withListener(object : AppUpdaterUtils.UpdateListener {
            override fun onSuccess(update: Update?, isUpdateAvailable: Boolean?) {
                Log.d("Latest Version", update?.latestVersion.toString())
                _recentVersion.value = "최신 버전 : ${update?.latestVersion}"
                url = "https://github.com/MondSeo/obigo-carmo/releases/download/v${update?.latestVersion}/Carmo_v${update?.latestVersion}.apk"
                latestVersion = update?.latestVersion.toString()
                Log.d("Latest Version", url)
            }

            override fun onFailed(error: AppUpdaterError?) {
                _recentVersion.value = context.getString(R.string.defaultRecentVersion)
                Log.d(TAG, "$error");
            }
        })

    /**
     * 가공 데이터
     */
    fun initData(){
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

    /**
     * 정류장 이동
     */
    fun stationMoving(view: View){
        var i = 0
        if(!isTimerRunning){
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    try {
                        _currentStation.postValue(station[i])
                    } catch(e : IndexOutOfBoundsException){
                        i = 0
                        _currentStation.postValue(station[i])
                    }
                    animationFunctions.dropDownAnimationChanged(view, selectedAnimation)
                    i++
                }
            }, 0,3000)
            isTimerRunning = true
        }
    }
}