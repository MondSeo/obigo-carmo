package com.obigo.carmo.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.AppUpdaterUtils
import com.github.javiersantos.appupdater.BuildConfig
import com.github.javiersantos.appupdater.enums.AppUpdaterError
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.github.javiersantos.appupdater.objects.Update

class MainViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val _recentVersion = MutableLiveData<String>()
    val recentVersion : LiveData<String> get() = _recentVersion



    val appUpdater = AppUpdater(context)
        .setUpdateFrom(UpdateFrom.GITHUB)
        .setButtonUpdateClickListener { dialog, which ->

        }
        .setGitHubUserAndRepo("MondSeo","obigo-carmo-updater")


    val appUpdaterUtils : AppUpdaterUtils = AppUpdaterUtils(context)
        .setUpdateFrom(UpdateFrom.GITHUB)
        .setGitHubUserAndRepo("mondseo","obigo-carmo-updater")
        .withListener(object : AppUpdaterUtils.UpdateListener{
            override fun onSuccess(update: Update?, isUpdateAvailable: Boolean?) {
                Log.d("Latest Version", update?.latestVersion.toString());
//             recentVersion = update?.latestVersion.toString()
                _recentVersion.value = "최신 버전 : ${update?.latestVersion}"
                Log.d("Latest Version Code", update?.latestVersionCode.toString());
                Log.d("Release notes", update?.releaseNotes.toString())
                Log.d("URL", update?.urlToDownload.toString())
            }

            override fun onFailed(error: AppUpdaterError?) {
                _recentVersion.value = "최신 버전 : -"
                Log.d(TAG, "$error");
            }
        })

}