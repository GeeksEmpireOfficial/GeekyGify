/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/7/20 3:48 PM
 * Last modified 2/7/20 2:33 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data

data class CategoryItemDataLeft(var categoryTitle: String?, var backgroundColor: Int)
data class CategoryItemDataRight(var categoryTitle: String?, var  backgroundColor: Int)
data class CategoryItemData (var categoryLeft: CategoryItemDataLeft?, var categoryRight: CategoryItemDataRight?)