/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/4/20 3:21 PM
 * Last modified 2/4/20 3:18 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.Data.CategoryItemData

class CategoryBrowserViewModel : ViewModel() {

    val categoriesListData: MutableLiveData<ArrayList<CategoryItemData>> by lazy {
        MutableLiveData<ArrayList<CategoryItemData>>()
    }
}