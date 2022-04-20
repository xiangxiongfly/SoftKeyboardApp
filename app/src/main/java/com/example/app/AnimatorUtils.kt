package com.example.app

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator

const val TRANSLATION_Y = "translationY"
const val SCALE_X = "scaleX"
const val SCALE_Y = "scaleY"

object AnimatorUtils {

    /**
     * Y轴移动动画
     */
    fun translationY(view: View, from: Float, to: Float) {
        ObjectAnimator.ofFloat(view, TRANSLATION_Y, from, to).apply {
            interpolator = LinearInterpolator()
            duration = 300L
        }.start()
    }

    /**
     * 缩放动画
     */
    fun scaleXY(view: View, fromScale: Float, toScale: Float) {
        view.apply {
            pivotX = (view.width / 2).toFloat()
            pivotY = (view.height / 2).toFloat()
        }
        val animatorScaleX = ObjectAnimator.ofFloat(view, SCALE_X, fromScale, toScale)
        val animatorScaleY = ObjectAnimator.ofFloat(view, SCALE_Y, fromScale, toScale)
        AnimatorSet().apply {
            duration = 300L
            playTogether(animatorScaleX, animatorScaleY);
        }.start()
    }
}