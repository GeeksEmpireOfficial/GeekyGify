/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/6/20 4:27 PM
 * Last modified 2/6/20 4:06 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.RetrieveResources

import android.content.Context
import net.geeksempire.geeky.gify.R

class GetResources(var context: Context) {

    fun getNeonColors() = context.resources.getStringArray(R.array.neonColors).toList() as ArrayList<String>
}