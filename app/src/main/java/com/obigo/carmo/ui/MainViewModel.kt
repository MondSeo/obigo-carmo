package com.obigo.carmo.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.javiersantos.appupdater.AppUpdaterUtils
import com.github.javiersantos.appupdater.enums.AppUpdaterError
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.github.javiersantos.appupdater.objects.Update
import com.obigo.carmo.R

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
}