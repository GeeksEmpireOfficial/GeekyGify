/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/3/20 2:00 AM
 * Last modified 3/3/20 1:53 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.ServerConnection.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.json.JSONObject

class BrowseGifViewModel : ViewModel() {

    val gifsListData: MutableLiveData<ArrayList<BrowseGifItemData>> by lazy {
        MutableLiveData<ArrayList<BrowseGifItemData>>()
    }

    val gifsListError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun setupGifsBrowserData(rawDataJsonObject: JSONObject, colorsList: ArrayList<String>) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {


    }
}