/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/24/20 8:43 PM
 * Last modified 2/24/20 6:40 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data

data class CategoryItemDataLeft(var categoryTitle: String?, var backgroundColor: Int)
data class CategoryItemDataRight(var categoryTitle: String?, var  backgroundColor: Int)
data class CategoryItemData(var viewType: String, var categoryLeft: CategoryItemDataLeft?, var categoryRight: CategoryItemDataRight?)

data class CategoryListItemType(var itemType: String, var itemTitle: String?)