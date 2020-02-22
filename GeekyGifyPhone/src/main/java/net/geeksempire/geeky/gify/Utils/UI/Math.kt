/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/21/20 6:46 PM
 * Last modified 2/21/20 5:15 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.UI

import android.graphics.Color
import kotlin.math.roundToInt

fun setColorAlpha(color: Int, alphaValue: Float /*1 -- 255*/): Int {
    val alpha = (Color.alpha(color) * alphaValue).roundToInt()
    val red = Color.red(color)
    val green = Color.green(color)
    val blue = Color.blue(color)
    return Color.argb(alpha, red, green, blue)
}