/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/14/20 4:26 PM
 * Last modified 2/14/20 3:02 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.ServerConnections

import org.json.JSONObject

interface JsonRequestResponseInterface {
    fun jsonRequestResponseSuccessHandler(rawDataJsonObject: JSONObject, colorsList: ArrayList<String>)
    fun jsonRequestResponseFailureHandler(jsonError: String)
}