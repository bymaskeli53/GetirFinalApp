package com.example.getirfinalapp.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.view.animation.BounceInterpolator
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.getirfinalapp.R

object PriceAnimationHelper {
    // Basit animasyon
    fun animateTextView(textView: TextView, startValue: Double, endValue: Double, duration: Long = 1000) {
        val animator = ValueAnimator.ofFloat(startValue.toFloat(), endValue.toFloat())
        animator.duration = duration

        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            textView.text = PriceFormatter.formatPrice(value.toDouble())
        }

        animator.start()
    }

    // Gelişmiş animasyon (sayma efekti ile)
    fun animatePrice(
        textView: TextView,
        startPrice: Double,
        endPrice: Double,
        duration: Long = 500,
        interpolator: TimeInterpolator = BounceInterpolator()
    ) {
        val animator = ValueAnimator.ofObject(
            TypeEvaluator<Double> { fraction, startValue, endValue ->
                startValue + (endValue - startValue) * fraction
            },
            startPrice,
            endPrice
        )

        animator.duration = duration
        animator.interpolator = interpolator

        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Double
            textView.text = PriceFormatter.formatPrice(animatedValue)
        }

        // Opsiyonel: Animasyon başlangıç ve bitiş efektleri
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.price_updating))
            }

            override fun onAnimationEnd(animation: Animator) {
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.price_normal))
            }
        })

        animator.start()
    }
}

fun TextView.animatePrice(newPrice: Double) {
    val oldPrice = try {
        PriceFormatter.parsePrice(this.text.toString())
    } catch (e: Exception) {
        0.0
    }

    PriceAnimationHelper.animatePrice(
        textView = this,
        startPrice = oldPrice,
        endPrice = newPrice
    )
}
