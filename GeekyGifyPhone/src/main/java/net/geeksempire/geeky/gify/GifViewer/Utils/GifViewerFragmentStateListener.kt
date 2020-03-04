/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/4/20 11:25 AM
 * Last modified 3/4/20 11:20 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifViewer.Utils

sealed class ReloadData {
    object DataType_Collection : ReloadData()
    object DataType_Trend : ReloadData()
}

interface GifViewerFragmentStateListener {
    fun onFragmentDetach(reloadDataType: ReloadData) {}
}