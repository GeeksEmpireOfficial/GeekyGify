/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/4/20 9:55 AM
 * Last modified 3/4/20 9:55 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.CollectionSectionUI.Utils

import android.graphics.drawable.Drawable
import java.io.File

class CollectionFile {

    fun convertFileToDrawable(drawableFile: File) : Drawable? {

        return Drawable.createFromPath(drawableFile.absolutePath)
    }

    fun extractGifId(fileName: String) : String {
        //GeekyGify[ID].GIF

        return fileName
            .replace("GeekyGify", "")
            .replace(".GIF", "")
    }
}