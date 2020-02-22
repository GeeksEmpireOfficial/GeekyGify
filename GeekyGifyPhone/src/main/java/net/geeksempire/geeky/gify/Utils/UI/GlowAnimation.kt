/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/21/20 6:46 PM
 * Last modified 2/21/20 6:43 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.UI

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.TextView

class GlowAnimation (var context: Context){

    fun valueAnimatorLoopTint(view: ImageView,
                              startValueColor: Int, endValueColor: Int,
                              startDuration: Int, endDuration: Int) {

        val valueAnimator =
            ValueAnimator.ofArgb(
                startValueColor,
                endValueColor
            )
        valueAnimator.startDelay = 500
        valueAnimator.duration = startDuration.toLong()
        valueAnimator.addUpdateListener { animator ->
            val changedValue = (animator.animatedValue as Int)

            view.backgroundTintList = ColorStateList.valueOf(changedValue)
        }
        valueAnimator.start()
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                val valueAnimatorReverse =
                    ValueAnimator.ofArgb(
                        endValueColor,
                        startValueColor
                    )
                valueAnimatorReverse.duration = endDuration.toLong()
                valueAnimatorReverse.addUpdateListener { animator ->
                    val changedValue = (animator.animatedValue as Int)

                    view.backgroundTintList = ColorStateList.valueOf(changedValue)
                }
                valueAnimatorReverse.start()
                valueAnimatorReverse.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {

                        valueAnimatorLoopTint(
                            view = view,
                            startValueColor = startValueColor, endValueColor = endValueColor,
                            startDuration = startDuration, endDuration = endDuration
                        )
                    }

                    override fun onAnimationCancel(animation: Animator?) {

                    }

                    override fun onAnimationStart(animation: Animator?) {

                    }
                })
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
    }

    fun shadowValueAnimatorLoop(view: TextView,
                                startValueShadow: Int, endValueShadow: Int,
                                startDuration: Int, endDuration: Int,
                                shadowColor: Int, shadowX: Float, shadowY: Float) {

        val valueAnimator =
            ValueAnimator.ofInt(
                startValueShadow,
                endValueShadow
            )
        valueAnimator.startDelay = 1000
        valueAnimator.duration = startDuration.toLong()
        valueAnimator.addUpdateListener { animator ->
            //(animator.animatedValue as Int)

            view.setShadowLayer((animator.animatedValue as Int).toFloat(), shadowX, shadowY, shadowColor)
        }
        valueAnimator.start()
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                val primeNumbersGlowUp =
                    ValueAnimator.ofInt(
                        endValueShadow,
                        startValueShadow
                    )
                primeNumbersGlowUp.duration = endDuration.toLong()
                primeNumbersGlowUp.addUpdateListener { animator ->
                    //(animator.animatedValue as Int)

                    view.setShadowLayer((animator.animatedValue as Int).toFloat(), shadowX, shadowY,  shadowColor)
                }
                primeNumbersGlowUp.start()
                primeNumbersGlowUp.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        shadowValueAnimatorLoop(view, startValueShadow, endValueShadow, startDuration, endDuration, shadowColor, shadowX, shadowY)
                    }

                    override fun onAnimationCancel(animation: Animator?) {

                    }

                    override fun onAnimationStart(animation: Animator?) {

                    }
                })
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
    }
}