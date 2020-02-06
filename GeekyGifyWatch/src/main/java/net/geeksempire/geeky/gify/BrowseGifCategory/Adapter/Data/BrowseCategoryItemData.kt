/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/6/20 11:14 AM
 * Last modified 2/6/20 11:07 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.Data

data class CategoryItemDataLeft(var categoryTitle: String?, var backgroundColor: Int)
data class CategoryItemDataRight(var categoryTitle: String?, var  backgroundColor: Int)
data class CategoryItemData (var categoryLeft: CategoryItemDataLeft?, var categoryRight: CategoryItemDataRight?)