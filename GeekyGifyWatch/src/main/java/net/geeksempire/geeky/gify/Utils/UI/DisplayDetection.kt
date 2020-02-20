/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/19/20 3:13 PM
 * Last modified 2/19/20 2:32 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.UI

import android.view.View
import android.view.WindowInsets
import androidx.fragment.app.FragmentActivity

interface ShapeDetection {
    fun shapeType(typeOfShape: Int)
}

class DisplayDetection (var fragmentActivity: FragmentActivity) {

    object DisplaySection {
        const val TopLeft = 1
        const val TopRight = 2
        const val BottomLeft = 3
        const val BottomRight = 4
    }

    object DisplayType {
        const val ROUND_FULL = 1
        const val ROUND_CHIN = 2
        const val RECTANGLE = 3
    }

    var WATCH_SHAPE = DisplayType.ROUND_FULL

    fun displayX(): Int {
        return fragmentActivity.resources.displayMetrics.widthPixels
    }

    fun displayY(): Int {
        return fragmentActivity.resources.displayMetrics.heightPixels
    }

    fun displaySection(X: Int, Y: Int): Int {
        var section = 1
        if (X < displayX() / 2 && Y < displayY() / 2) { //top-left
            section = 1
        } else if (X > displayX() / 2 && Y < displayY() / 2) { //top-right
            section = 2
        } else if (X < displayX() / 2 && Y > displayY() / 2) { //bottom-left
            section = 3
        } else if (X > displayX() / 2 && Y > displayY() / 2) { //bottom-right
            section = 4
        }
        return section
    }

    fun initializeShapeDetection(shapeDetection: ShapeDetection) {
        fragmentActivity.window.decorView.findViewById<View>(android.R.id.content).setOnApplyWindowInsetsListener(object : View.OnApplyWindowInsetsListener {

            override fun onApplyWindowInsets(view: View, windowInsets: WindowInsets): WindowInsets {
                if (windowInsets.isRound) {
                    WATCH_SHAPE = DisplayType.ROUND_FULL

                    if (displayX() != displayY()) {
                        WATCH_SHAPE = DisplayType.ROUND_CHIN
                    }
                } else {
                    WATCH_SHAPE = DisplayType.RECTANGLE
                }

                shapeDetection.shapeType(WATCH_SHAPE)

                return windowInsets
            }
        })
    }
}