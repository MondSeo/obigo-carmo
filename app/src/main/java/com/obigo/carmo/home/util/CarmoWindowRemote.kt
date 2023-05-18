package com.obigo.carmo.home.util

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.core.content.ContextCompat
import com.obigo.carmo.home.service.CarmoWindowService
import java.util.WeakHashMap

object CarmoWindowRemote {


    var carmoWindowService : CarmoWindowService? = null
    private val mConnectionMap = WeakHashMap<Context, ServiceBinder>()

    fun changeStateChildQuit(){
        if(carmoWindowService != null){
            carmoWindowService?.changeStateChildQuit(CarmoState.CARMO_STATE_CHILD_QUIT)
        }
    }

    fun changeStatePetIdle(){
        if(carmoWindowService != null){
            carmoWindowService?.changeStatePetIdle(CarmoState.CARMO_STATE_PET_IDLE)
        }
    }

    fun changeStateQuit(){
        if(carmoWindowService != null){
            carmoWindowService?.changeStateQuit(CarmoState.CARMO_STATE_QUIT)
        }
    }

    fun bindToService(context: Context, callback: ServiceConnection): ServiceToken? {

        var realActivity: Activity? = (context as Activity).parent
        if (realActivity == null) {
            realActivity = context
        }

        val contextWrapper = ContextWrapper(realActivity)
        val intent = Intent(contextWrapper, CarmoWindowService::class.java)
        try {
            contextWrapper.startService(intent)
        } catch (ignored: IllegalStateException) {
            ContextCompat.startForegroundService(context, intent)
        }
        val binder = ServiceBinder(callback)

        if (contextWrapper.bindService(
                Intent().setClass(contextWrapper, CarmoWindowService::class.java),
                binder,
                Context.BIND_AUTO_CREATE
            )
        ) {
            mConnectionMap[contextWrapper] = binder
            return ServiceToken(contextWrapper)
        }
        return null
    }

    class ServiceBinder internal constructor(private val mCallback: ServiceConnection?) : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as CarmoWindowService.LocalBinder
            carmoWindowService = binder.getService()
            mCallback?.onServiceConnected(className, service)
        }

        override fun onServiceDisconnected(className: ComponentName) {
            mCallback?.onServiceDisconnected(className)
            carmoWindowService = null
        }
    }

    class ServiceToken internal constructor(internal var mWrappedContext: ContextWrapper)
}