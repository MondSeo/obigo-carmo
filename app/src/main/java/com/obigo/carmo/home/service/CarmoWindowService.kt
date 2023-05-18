package com.obigo.carmo.home.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.obigo.carmo.home.util.CarmoState
import com.obigo.carmo.home.util.CarmoWindowStateNotifier

class CarmoWindowService : Service() {

    private val iBinder: IBinder = LocalBinder()

    private lateinit var carmoWindowStateNotifier: CarmoWindowStateNotifier

    override fun onBind(intent: Intent?): IBinder? {
        return iBinder
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.action != null) {
            when (intent.action) {
                CarmoState.CARMO_STATE_CHILD_QUIT -> {
                    notifyCarmoWindowStateChanged(CarmoState.CARMO_STATE_CHILD_QUIT)
                }

                CarmoState.CARMO_STATE_PET_IDLE -> {
                    notifyCarmoWindowStateChanged(CarmoState.CARMO_STATE_PET_IDLE)
                }

                CarmoState.CARMO_STATE_QUIT -> {
                    notifyCarmoWindowStateChanged(CarmoState.CARMO_STATE_QUIT)
                }
                else -> Unit
            }
        }
        return START_NOT_STICKY
    }

    fun setCarmoWindowStateCallback(callback: CarmoWindowStateNotifier) {
        this.carmoWindowStateNotifier = callback
    }

    fun changeStateChildQuit(action : String) {
        this.changeStateDriveCarmoWindow(action)
    }


    fun changeStatePetIdle(action : String) {
        this.changeStatePetIdleCarmoWindow(action)
    }

    fun changeStateQuit(action : String) {
        this.changeStateQuitCarmoWindow(action)
    }

    private fun notifyCarmoWindowStateChanged(action :String) {
        carmoWindowStateNotifier.onCarmoWindowStateChange(action)
    }

    private fun changeStateDriveCarmoWindow(action : String) {
        notifyCarmoWindowStateChanged(action)
    }

    private fun changeStatePetIdleCarmoWindow(action : String) {
        notifyCarmoWindowStateChanged(action)
    }

    private fun changeStateQuitCarmoWindow(action : String) {
        notifyCarmoWindowStateChanged(action)
    }

    inner class LocalBinder : Binder() {
        fun getService(): CarmoWindowService {
            return this@CarmoWindowService
        }
    }
}