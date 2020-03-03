/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/3/20 2:00 AM
 * Last modified 3/3/20 2:00 AM
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