/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/6/20 4:27 PM
 * Last modified 2/6/20 4:25 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Data

import android.content.res.Resources
import net.geeksempire.geeky.gify.R

class BrowseGitCategoryData {

    fun categoryListResource(resources: Resources) : ArrayList<String> {
        return resources.getStringArray(R.array.gifCategoryList).toList() as ArrayList<String>
    }
}