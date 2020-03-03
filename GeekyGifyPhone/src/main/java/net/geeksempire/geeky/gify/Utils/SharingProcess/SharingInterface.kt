/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/3/20 6:52 AM
 * Last modified 3/3/20 6:28 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.SharingProcess

interface SharingInterface {
    fun sharingProcessCallback(gifLinkToShare: String, additionalText: String?)
}