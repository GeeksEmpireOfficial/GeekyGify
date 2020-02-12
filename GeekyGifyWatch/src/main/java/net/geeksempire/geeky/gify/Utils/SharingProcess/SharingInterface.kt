/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/12/20 1:13 PM
 * Last modified 2/12/20 11:52 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.SharingProcess

interface SharingInterface {
    fun sharingProcessCallback(gifLinkToShare: String, additionalText: String?)
}