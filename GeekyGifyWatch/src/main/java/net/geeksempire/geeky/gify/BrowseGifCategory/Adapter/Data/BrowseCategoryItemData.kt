/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/4/20 3:48 PM
 * Last modified 2/4/20 3:21 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.Data

data class CategoryItemDataLeft(var categoryTitle: String?)
data class CategoryItemDataRight(var categoryTitle: String?)
data class CategoryItemData (var categoryLeft: CategoryItemDataLeft?, var categoryRight: CategoryItemDataRight?)