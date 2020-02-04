/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/4/20 2:10 PM
 * Last modified 2/4/20 2:06 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.geeksempire.geeky.gify.BrowseGifCategory.Data.CategoryItemData
import net.geeksempire.geeky.gify.R

class CategoryAdapter(var context: Context, var categoryItemsData: ArrayList<CategoryItemData>) : RecyclerView.Adapter<CategoryListViewHolder>() {

    val colorsList = arrayOf(Color.BLACK, Color.RED, Color.GRAY, Color.GREEN, Color.BLUE, Color.CYAN, Color.YELLOW, Color.MAGENTA)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {

        return CategoryListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category_view, parent, false))
    }

    override fun getItemCount(): Int {

        return categoryItemsData.size
    }

    override fun onBindViewHolder(holderCategoryList: CategoryListViewHolder, position: Int) {

        val backgroundDrawableLeftLayer = context.getDrawable(R.drawable.category_left_background) as LayerDrawable
        val backgroundDrawableLeft = backgroundDrawableLeftLayer.findDrawableByLayerId(R.id.drawableBackground)
        backgroundDrawableLeft?.let {
            it.setTint(colorsList.random())
            holderCategoryList.categoryIconLeft.background = backgroundDrawableLeftLayer
        }
        holderCategoryList.categoryIconLeft.text = (categoryItemsData[position].categoryTitleLeft)

        holderCategoryList.categoryIconLeft.setOnClickListener {

        }


        val backgroundDrawableRightLayer = context.getDrawable(R.drawable.category_right_background) as LayerDrawable
        val backgroundDrawableRight = backgroundDrawableRightLayer.findDrawableByLayerId(R.id.drawableBackground)
        backgroundDrawableRight?.let {
            it.setTint(colorsList.random())
            holderCategoryList.categoryIconRight.background = backgroundDrawableRightLayer
        }
        holderCategoryList.categoryIconRight.text = (categoryItemsData[position].categoryTitleRight)

        holderCategoryList.categoryIconRight.setOnClickListener {

        }
    }
}