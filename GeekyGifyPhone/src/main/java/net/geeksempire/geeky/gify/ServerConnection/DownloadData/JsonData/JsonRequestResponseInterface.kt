/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/3/20 2:00 AM
 * Last modified 2/20/20 12:35 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.Networking.ServerConnections

import org.json.JSONObject

interface JsonRequestResponseInterface {
    fun jsonRequestResponseSuccessHandler(rawDataJsonObject: JSONObject, colorsList: ArrayList<String>)
    fun jsonRequestResponseFailureHandler(jsonError: String)
}