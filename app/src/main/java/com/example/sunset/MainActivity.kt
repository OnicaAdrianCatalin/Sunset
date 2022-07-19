package com.example.sunset

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var sceneView: View
    private lateinit var sunView: View
    private lateinit var skyView: View

    private val blueSkyColor by lazy {
        ContextCompat.getColor(this, R.color.blue_sky)
    }
    private val sunsetSkyColor by lazy {
        ContextCompat.getColor(this, R.color.sunset_sky)
    }
    private val nightSkyColor by lazy {
        ContextCompat.getColor(this, R.color.night_sky)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        setOnClickListeners()
    }

    private fun startAnimation() {
        val sunYStart = sunView.top.toFloat()
        val sunYEnd = skyView.height.toFloat()
        val heightAnimator =
            ObjectAnimator.ofFloat(sunView, "y", sunYStart, sunYEnd)
                .setDuration(heightAnimatorDuration)
        val sunsetSkyAnimator =
            ObjectAnimator.ofInt(skyView, "backgroundColor", blueSkyColor, sunsetSkyColor).apply {
                duration = sunsetSkyAnimatorDuration
                setEvaluator(ArgbEvaluator())
            }
        val nightSkyAnimator =
            ObjectAnimator.ofInt(skyView, "backgroundColor", sunsetSkyColor, nightSkyColor).apply {
                duration = nightSkyAnimatorDuration
                setEvaluator(ArgbEvaluator())
                interpolator = AccelerateInterpolator()
            }
        val animatorSet = AnimatorSet()
        animatorSet.play(heightAnimator).with(sunsetSkyAnimator).before(nightSkyAnimator)
        animatorSet.start()
    }

    private fun setOnClickListeners() {
        sceneView.setOnClickListener {
            startAnimation()
        }
    }

    private fun bindViews() {
        sceneView = findViewById(R.id.scene)
        sunView = findViewById(R.id.sun)
        skyView = findViewById(R.id.sky)
    }

    companion object {
        private const val heightAnimatorDuration = 3000L
        private const val sunsetSkyAnimatorDuration = 3000L
        private const val nightSkyAnimatorDuration = 1500L
    }
}
