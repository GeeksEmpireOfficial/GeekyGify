/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/8/20 10:06 AM
 * Last modified 2/8/20 9:36 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.ServerConnections

import org.json.JSONObject

interface JsonRequestResponseInterface {
    fun jsonRequestResponseHandler(rawDataJsonObject: JSONObject, colorsList: ArrayList<String>)
}