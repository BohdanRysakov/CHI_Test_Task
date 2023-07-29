package com.example.chi_test_task

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.chi_test_task.model.CloudsBackground
import com.example.chi_test_task.model.createClouds


class MainActivity : AppCompatActivity() {
    private val lightningChanceValue = 25
    private lateinit var lightningImage: ImageView
    private lateinit var rainbowImage: ImageView
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var clouds: Array<CloudsBackground>
    private val rainbowRunnable = Runnable {
        val fadeOut = ObjectAnimator.ofFloat(rainbowImage, "alpha", 0f, 0.5f)
        fadeOut.duration = 10000
        fadeOut.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val counter = findViewById<TextView>(R.id.counter)
        val layout = findViewById<ConstraintLayout>(R.id.myLayout) as ConstraintLayout
        val btnClickMe = findViewById<Button>(R.id.btn_click_me)
        lightningImage = findViewById(R.id.lightning)
        rainbowImage = findViewById(R.id.rainbow)
        rainbowImage.alpha = 0F;
        lightningImage.visibility = View.INVISIBLE
        counter.bringToFront();
        println(R.dimen.cloud1_width)
        println(resources.getDimensionPixelSize(R.dimen.cloud1_width))
        //animateCloud(cloud1)

        clouds = createClouds(this)

        for (cloud in clouds) {
            layout.addView(cloud)
            cloud.startAnimation()
        }


        val lvl1 = findViewById<TextView>(R.id.lvl1)
        val simpleClicker = findViewById<TextView>(R.id.simpleClicker)
        lvl1.bringToFront()
        simpleClicker.bringToFront()
        var count = 0;




        btnClickMe.setOnClickListener {
            count++;
            counter.text = "Tears Count: $count"
            val randomX =  btnClickMe.x + (0 .. btnClickMe.width).random()
            val randomY =  btnClickMe.y + btnClickMe.height + (-50 .. 50).random()
            startRainbowCountdown()
            val newDrop = ImageView(this).apply {
                setImageResource(R.drawable.drop)
                layoutParams = ViewGroup.LayoutParams(
                    40,
                    50
                )
                x = randomX
                y = randomY
            }

            layout.addView(newDrop)




            btnClickMe.text = "";
            val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
            btnClickMe.startAnimation(shake)
            val lightningChance = (0..100).random()
            if (lightningChance <= lightningChanceValue) {
                lightningImage.visibility = View.VISIBLE
                val fadeOut = ObjectAnimator.ofFloat(lightningImage, "alpha", 1f, 0f)
                fadeOut.duration = 1000
                fadeOut.start()
            }
            val randomDropTime = 1000 + (0 .. 1000).random()
            val dropAnimation = ObjectAnimator.ofFloat(newDrop, "translationY", layout.height.toFloat())
            dropAnimation.duration = randomDropTime.toLong()
            val fadeOut = ObjectAnimator.ofFloat(newDrop, "alpha", 1f, 0f)
            fadeOut.duration = randomDropTime.toLong()
            fadeOut.start()

            // Удаление ImageView после завершения анимации
            dropAnimation.addListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator) {
                    layout.removeView(newDrop)
                }
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}

            })

            // Запуск анимации
            dropAnimation.start()
        }
    }
    private fun startRainbowCountdown() {
        handler.postDelayed(rainbowRunnable, 0) // 10000 миллисекунд = 10 секунд
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(rainbowRunnable)
    }
}