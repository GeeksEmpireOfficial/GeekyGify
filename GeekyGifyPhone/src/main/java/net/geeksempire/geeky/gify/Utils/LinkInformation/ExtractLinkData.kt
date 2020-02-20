/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/19/20 5:35 PM
 * Last modified 2/19/20 5:26 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.LinkInformation

class ExtractLinkData {

    fun extractGifId (gifLink: String) : String {
        val dataToDelete = "https://media1.giphy.com/media/"

        return gifLink.split("/giphy.gif?")[0].replace(dataToDelete, "")
    }
}