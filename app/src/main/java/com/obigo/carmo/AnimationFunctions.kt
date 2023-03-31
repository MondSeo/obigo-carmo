package com.obigo.carmo

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils

/**
 * 애니메이션 집합 클래스
 */
class AnimationFunctions(val context: Context)  {
    fun startSampleAnimation(view : View){
        AnimationUtils.loadAnimation(context, R.anim.animation_sample).also { hyperspaceJumpAnimation ->
            view.startAnimation(hyperspaceJumpAnimation)
        }
    }

}