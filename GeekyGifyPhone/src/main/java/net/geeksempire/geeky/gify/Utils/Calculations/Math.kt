/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/5/20 8:01 AM
 * Last modified 3/5/20 7:21 AM
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
    return context.resources.displayMetrics.widthPixels
}

fun displayY(context: Context): Int {
    return context.resources.displayMetrics.heightPixels
}

fun columnCount(itemWidth: Int, context: Context): Int {
    return (displayX(context) / DpToPixel(itemWidth.toFloat(), context)).toInt()
}

fun rowCount(parentHeight: Int, itemHeight: Int): Int {
    val rowResult = (parentHeight / itemHeight)

    return if (rowResult == 0) {
        1
    } else {
        rowResult
    }
}