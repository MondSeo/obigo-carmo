package com.obigo.carmo.home.animation

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import com.obigo.carmo.home.R
import com.obigo.carmo.home.ui.MainViewModel

/**
 * 애니메이션 집합 클래스
 */
class AnimationFunctions(private val context: Context, private val mainViewModel: MainViewModel)  {

    private fun startAnimationTornado(view : View){
        AnimationUtils.loadAnimation(context, R.anim.anim_tornado).also { hyperspaceJumpAnimation ->
            hyperspaceJumpAnimation.isFillEnabled = false
            view.startAnimation(hyperspaceJumpAnimation)

        }
    }

    private fun startAnimationTranslate(view: View){

        AnimationUtils.loadAnimation(context,
            R.anim.anim_translate_fromcenter_toright
        ).also { hyperspaceJumpAnimation ->
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
                mainViewModel.animationPeriod = 3000
                startAnimationTornado(view)
            }
            1 ->{
                mainViewModel.animationPeriod = 3000
                startAnimationTranslate(view)
            }
            2 -> {
                mainViewModel.animationPeriod = 3000
                startAnimationBlink(view)
            }
            3 -> {
                mainViewModel.animationPeriod = 3000
                startAnimationBounce(view)
            }
            4 -> {
                mainViewModel.animationPeriod = 3000
                startAnimationSlideUp(view)
            }
            5 -> {
                mainViewModel.animationPeriod = 3000
                startAnimationSlideDown(view)
            }
            6 -> {
                mainViewModel.animationPeriod = 3000
                startAnimationRotate(view)
            }
            7 -> {
                mainViewModel.animationPeriod = 10000
                startAnimationMarquee(view)
            }
            8 -> {
                mainViewModel.animationPeriod = 3000
                startAnimationFlip(view)
            }
            9 -> {
                mainViewModel.animationPeriod = 3000
                startAnimationTogether(view)
            }
        }
    }
}