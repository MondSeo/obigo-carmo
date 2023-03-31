package com.obigo.carmo

import android.view.View

/**
 * 3초안에 특정 View를 10번 클릭 시 설정 화면으로 넘어가는 히든 클릭 리스너
 */
abstract class OnHiddenClickCountListener : View.OnClickListener {
    companion object {
          /**
          * 지정한 시간(CLICK_INTERVAL)이내에 지정횟수(CLICK_NUMBER)만큼 클릭 시에만 카운트
          */
        const val CLICK_INTERVAL:Long = 3000L
        const val CLICK_NUMBER = 10
    }
    /**
     * 새로 클릭한 시간
     */
    private var lastClickedTime: Long = 0L

    /**
     * 클릭 카운트
     */
    var count = 0

    abstract fun onCountClick(view: View)

    private fun isClickedTime() = System.currentTimeMillis() - lastClickedTime

    override fun onClick(v: View) {

        /**
         * CLICK_INTERVAL 시간 이내로 안누르면 카운트 초기화
         */
        if (isClickedTime() > CLICK_INTERVAL) {
            count = 0
        }

        lastClickedTime = System.currentTimeMillis()

        count += 1
        /**
         *  10번 클릭 시에만 추상메소드 실행
         */
        if (count == CLICK_NUMBER) {
            onCountClick(v)
            count = 0   // 카운트 초기화
        }


    }

}