/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/20/20 2:24 PM
 * Last modified 2/20/20 1:13 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.LinkInformation

class ExtractLinkData {

    fun extractGifId (gifLink: String) : String {

        return gifLink.split("/giphy.gif?")[0].split("/media/")[1]
    }
}