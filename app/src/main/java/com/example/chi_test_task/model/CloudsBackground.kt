package com.example.chi_test_task.model

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.annotation.DrawableRes
import com.example.chi_test_task.Interface.Animatable
import com.example.chi_test_task.R

class CloudsBackground(context: Context, @DrawableRes cloudDrawable: Int, widthRes: Int, heightRes: Int) : androidx.appcompat.widget.AppCompatImageView(context), Animatable {
    init {
        setImageResource(cloudDrawable)
        val widthPx = resources.getDimensionPixelSize(widthRes)
        val heightPx = resources.getDimensionPixelSize(heightRes)
        alpha = 0.5f
        layoutParams = ViewGroup.LayoutParams(
            widthPx,
            heightPx
        )
    }

    override fun startAnimation() {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels

        // Задаем анимацию облаку
        val animator = ObjectAnimator.ofFloat(this, "translationX", -screenWidth.toFloat(), screenWidth.toFloat())
        animator.duration = (15..45).random() * 1000L // Рандомная продолжительность от 2 до 5 секунд
        animator.interpolator = LinearInterpolator() // Равномерное движение
        animator.repeatCount = Animation.INFINITE // Бесконечное повторение
        animator.repeatMode = ValueAnimator.RESTART // Перезапуск анимации после окончания

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator) {
                val delay = (0..10).random() * 1000L // Рандомная задержка до следующего повтора от 0 до 2 секунд
                animation.startDelay = delay
            }

            override fun onAnimationEnd(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationStart(animation: Animator) {}
        })

        animator.start()
    }
}

fun createClouds(context: Context) : Array<CloudsBackground> {
    return arrayOf(
        CloudsBackground(context, R.drawable.cloud1, R.dimen.cloud1_width, R.dimen.cloud1_height),
        CloudsBackground(context, R.drawable.cloud2, R.dimen.cloud2_width, R.dimen.cloud2_height),
        CloudsBackground(context, R.drawable.cloud3, R.dimen.cloud3_width, R.dimen.cloud3_height),
        CloudsBackground(context, R.drawable.cloud4, R.dimen.cloud4_width, R.dimen.cloud4_height),
        CloudsBackground(context, R.drawable.cloud5, R.dimen.cloud5_width, R.dimen.cloud5_height)
    )
}
