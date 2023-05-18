package com.obigo.carmo.home.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.obigo.carmo.home.service.CarmoWindowService
import com.obigo.carmo.home.util.CarmoState
import java.lang.IllegalStateException

class CarmoWindowNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action != null){
            when(intent.action){
                CarmoState.CARMO_STATE_CHILD_QUIT -> {
                    startService(context!!, CarmoState.CARMO_STATE_CHILD_QUIT)
                }
                CarmoState.CARMO_STATE_PET_IDLE -> {
                    startService(context!!, CarmoState.CARMO_STATE_PET_IDLE)
                }
                CarmoState.CARMO_STATE_QUIT -> {
                    startService(context!!, CarmoState.CARMO_STATE_QUIT)
                }
                else -> Unit
            }
        }
    }

    private fun startService(context: Context, command: String?){
        val intent = Intent(context, CarmoWindowService::class.java)
        intent.action = command
        try {
            context.startService(intent)
        } catch (ignored: IllegalStateException){
            ContextCompat.startForegroundService(context,intent)
        }
    }
}