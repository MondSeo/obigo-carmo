package com.obigo.carmo.home.util

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import androidx.lifecycle.LiveData

/**
 * 인터넷이 되는지 안되는지 확인하기 위한 LiveData 클래스
 */
class NetworkConnection(private val context: Context) : LiveData<Boolean>()
{

    /**
     * 시스템에서 연결 상태를 체크하기 위한 매니저
     */
    private var connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /**
     * 네트워크 콜백
     */
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onActive()
    {
        super.onActive()
        updateConnection()
        when
        {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ->
            {
                connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback())
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ->
            {
                lollipopNetworkRequest()
            }
            else ->
            {
                context.registerReceiver(
                    networkReceiver,
                    IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                )
            }
        }
    }

    /**
     * 디바이스 버전 체크
     */
    override fun onInactive()
    {
        super.onInactive()
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                connectivityManager.unregisterNetworkCallback(connectivityManagerCallback())
            } else
            {
                context.unregisterReceiver(networkReceiver)
            }
        }catch (e: java.lang.Exception)
        {
            return
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lollipopNetworkRequest()
    {
        val requestBuilder = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        connectivityManager.registerNetworkCallback(
            requestBuilder.build(),
            connectivityManagerCallback()
        )
    }

    private fun connectivityManagerCallback(): ConnectivityManager.NetworkCallback
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            networkCallback = object : ConnectivityManager.NetworkCallback()
            {
                override fun onLost(network: Network)
                {
                    super.onLost(network)
                    postValue(false)
                }

                override fun onAvailable(network: Network)
                {
                    super.onAvailable(network)
                    postValue(true)
                }
            }
            return networkCallback
        } else
        {
            throw IllegalAccessError("Error")
        }
    }

    private val networkReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context?, intent: Intent?)
        {
            updateConnection()
        }
    }

    private fun updateConnection()
    {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnected == true)
    }

}