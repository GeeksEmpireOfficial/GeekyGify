/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/3/20 5:11 AM
 * Last modified 3/3/20 5:06 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.Calculations

import android.content.Context

fun numberEven(aNumber: Int) : Boolean {
    return (aNumber % 2 == 0)
}

fun calculateThirtyPercent(currentNumber: Int) : Int {

    return (currentNumber - ((currentNumber * 30)/100))
}

fun displayX(context: Context): Int {
    return context.getResources().getDisplayMetrics().widthPixels
}

fun displayY(context: Context): Int {
    return context.getResources().getDisplayMetrics().heightPixels
}

fun columnCount(itemWidth: Int, context: Context): Int {
    return (displayX(context) / DpToPixel(itemWidth.toFloat(), context)).toInt()
}