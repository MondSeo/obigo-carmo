package com.obigo.carmo

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils

/**
 * 애니메이션 집합 클래스
 */
class AnimationFunctions(val context: Context)  {

    private fun startAnimationTornado(view : View){
        AnimationUtils.loadAnimation(context, R.anim.anim_tornado).also { hyperspaceJumpAnimation ->
            hyperspaceJumpAnimation.isFillEnabled = false
            view.startAnimation(hyperspaceJumpAnimation)
        }
    }

    private fun startAnimationTranslate(view: View){
        AnimationUtils.loadAnimation(context, R.anim.anim_translate_fromcenter_toright).also { hyperspaceJumpAnimation ->
            hyperspaceJumpAnimation.isFillEnabled = false
            view.startAnimation(hyperspaceJumpAnimation)
        }
    }

    private fun startAnimationBlink(view: View){
        AnimationUtils.loadAnimation(context, R.anim.anim_blink).also { hyperspaceJumpAnimation ->
            hyperspaceJumpAnimation.isFillEnabled = false
            view.startAnimation(hyperspaceJumpAnimation)
        }
    }

    private fun startAnimationBounce(view: View){
        AnimationUtils.loadAnimation(context, R.anim.anim_bounce).also { hyperspaceJumpAnimation ->
            hyperspaceJumpAnimation.isFillEnabled = false
            view.startAnimation(hyperspaceJumpAnimation)
        }
    }

    private fun startAnimationSlideUp(view: View){
        AnimationUtils.loadAnimation(context, R.anim.anim_slide_up).also { hyperspaceJumpAnimation ->
            hyperspaceJumpAnimation.isFillEnabled = false
            view.startAnimation(hyperspaceJumpAnimation)
        }
    }
    private fun startAnimationSlideDown(view: View){
        AnimationUtils.loadAnimation(context, R.anim.anim_slide_down).also { hyperspaceJumpAnimation ->
            hyperspaceJumpAnimation.isFillEnabled = false
            view.startAnimation(hyperspaceJumpAnimation)
        }
    }

    private fun startAnimationRotate(view: View){
        AnimationUtils.loadAnimation(context, R.anim.anim_rotate).also { hyperspaceJumpAnimation ->
            hyperspaceJumpAnimation.isFillEnabled = false
            view.startAnimation(hyperspaceJumpAnimation)
        }
    }

    private fun startAnimationMarquee(view : View){
        AnimationUtils.loadAnimation(context, R.anim.anim_marquee).also { hyperspaceJumpAnimation ->
            hyperspaceJumpAnimation.isFillEnabled = false
            view.startAnimation(hyperspaceJumpAnimation)
        }
    }

    private fun startAnimationFlip(view : View){
        AnimationUtils.loadAnimation(context, R.anim.anim_flip).also { hyperspaceJumpAnimation ->
            hyperspaceJumpAnimation.isFillEnabled = false
            view.startAnimation(hyperspaceJumpAnimation)
        }
    }

    private fun startAnimationTogether(view : View){
        AnimationUtils.loadAnimation(context, R.anim.anim_together).also { hyperspaceJumpAnimation ->
            hyperspaceJumpAnimation.isFillEnabled = false
            view.startAnimation(hyperspaceJumpAnimation)
        }
    }

    /**
     * 드랍다운 애니메이션 변경
     */
    fun dropDownAnimationChanged(view : View, position: Int){
        when(position){
            0 ->{
                startAnimationTornado(view)
            }
            1 ->{
                startAnimationTranslate(view)
            }
            2 -> {
                startAnimationBlink(view)
            }
            3 -> {
                startAnimationBounce(view)
            }
            4 -> {
                startAnimationSlideUp(view)
            }
            5 -> {
                startAnimationSlideDown(view)
            }
            6 -> {
                startAnimationRotate(view)
            }
            7 -> {
                startAnimationMarquee(view)
            }
            8 -> {
                startAnimationFlip(view)
            }
            9 -> {
                startAnimationTogether(view)
            }
        }
    }
}