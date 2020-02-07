/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/6/20 4:27 PM
 * Last modified 2/6/20 4:08 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.BrowseGif.Adapter.Data.BrowseGifItemData
import org.json.JSONObject

class BrowseGifViewModel : ViewModel() {

    val gifsListData: MutableLiveData<ArrayList<BrowseGifItemData>> by lazy {
        MutableLiveData<ArrayList<BrowseGifItemData>>()
    }

    fun setupGifsBrowserData(rawDataJsonObject: JSONObject, colorsList: ArrayList<String>) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {



    }
}