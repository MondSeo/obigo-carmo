package com.obigo.carmoupdater.ui

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.javiersantos.appupdater.AppUpdaterUtils
import com.github.javiersantos.appupdater.enums.AppUpdaterError
import com.github.javiersantos.appupdater.objects.Update

class MainViewModel(application: Application) : AndroidViewModel(application),
    AppUpdaterUtils.UpdateListener {

    private val _recentVersion = MutableLiveData<String>()
    val recentVersion : LiveData<String> get() = _recentVersion



    override fun onSuccess(update: Update?, isUpdateAvailable: Boolean?) {
        Log.d("Latest Version", update?.latestVersion.toString());
//             recentVersion = update?.latestVersion.toString()
        _recentVersion.value = "최신버전 : ${update?.latestVersion}"
        Log.d("Latest Version Code", update?.latestVersionCode.toString());
        Log.d("Release notes", update?.releaseNotes.toString())
        Log.d("URL", update?.urlToDownload.toString())
    }

    override fun onFailed(error: AppUpdaterError?) {
        _recentVersion.value = "최신버전 : -"
        Log.d(TAG, "$error");
    }
}