package com.obigo.carmo

import android.view.View

abstract class OnClickCountListener : View.OnClickListener {
    companion object {
        // 지정한 시간이내에 지정횟수만큼 클릭 시에만 카운트
        const val CLICK_INTERVAL:Long = 3000L
        const val CLICK_NUMBER = 10
    }

    private var lastClickedTime: Long = 0L // 새로 클릭한 시간
    var count = 0 // 클릭 카운트

    abstract fun onCountClick(view: View)

    private fun isClickedTime() = System.currentTimeMillis() - lastClickedTime

    override fun onClick(v: View) {

        // CLICK_INTERVAL 시간 이내로 안누르면 카운트 초기화
        if (isClickedTime() > CLICK_INTERVAL) {
            count = 0
        }

        lastClickedTime = System.currentTimeMillis()

        count += 1

        if (count == CLICK_NUMBER) { // 5번 클릭 시에만 추상메소드 실행
            onCountClick(v)
            count = 0   // 카운트 초기화
        }


    }

}