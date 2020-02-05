/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/4/20 3:48 PM
 * Last modified 2/4/20 3:47 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.Data.CategoryItemData
import net.geeksempire.geeky.gify.R

class BrowseCategoryAdapter(var context: Context, var categoryItemsData: ArrayList<CategoryItemData>) : RecyclerView.Adapter<BrowseCategoryListViewHolder>() {

    val colorsList =
        arrayOf(
            Color.BLACK,
            Color.RED,
            Color.GRAY,
            Color.GREEN,
            Color.BLUE,
            Color.CYAN,
            Color.YELLOW,
            Color.MAGENTA)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseCategoryListViewHolder {

        return BrowseCategoryListViewHolder(LayoutInflater.from(context).inflate(R.layout.browse_gif_category_item_view, parent, false))
    }

    override fun getItemCount(): Int {

        return categoryItemsData.size
    }

    override fun onBindViewHolder(viewHolderBrowseCategoryList: BrowseCategoryListViewHolder, position: Int) {

        if (categoryItemsData[position].categoryLeft != null) {
            viewHolderBrowseCategoryList.categoryIconLeft.visibility = View.VISIBLE

            viewHolderBrowseCategoryList.categoryIconLeft.text = (categoryItemsData[position].categoryLeft?.categoryTitle)

            val backgroundDrawableLeftLayer = context.getDrawable(R.drawable.category_left_background) as LayerDrawable
            val backgroundDrawableLeft = backgroundDrawableLeftLayer.findDrawableByLayerId(R.id.drawableBackground)
            backgroundDrawableLeft?.let {
                it.setTint(colorsList.random())
                viewHolderBrowseCategoryList.categoryIconLeft.background = backgroundDrawableLeftLayer
            }
            viewHolderBrowseCategoryList.categoryIconLeft.setOnClickListener {
                Log.d("CategoryAdapter", categoryItemsData[position].categoryLeft?.categoryTitle)

            }
        } else {
            viewHolderBrowseCategoryList.categoryIconLeft.visibility = View.GONE
        }

        if (categoryItemsData[position].categoryRight != null) {
            viewHolderBrowseCategoryList.categoryIconRight.visibility = View.VISIBLE

            viewHolderBrowseCategoryList.categoryIconRight.text = (categoryItemsData[position].categoryRight?.categoryTitle)

            val backgroundDrawableRightLayer = context.getDrawable(R.drawable.category_right_background) as LayerDrawable
            val backgroundDrawableRight = backgroundDrawableRightLayer.findDrawableByLayerId(R.id.drawableBackground)
            backgroundDrawableRight?.let {
                it.setTint(colorsList.random())
                viewHolderBrowseCategoryList.categoryIconRight.background = backgroundDrawableRightLayer
            }

            viewHolderBrowseCategoryList.categoryIconRight.setOnClickListener {
                Log.d("CategoryAdapter", categoryItemsData[position].categoryRight?.categoryTitle)

            }
        } else {
            viewHolderBrowseCategoryList.categoryIconRight.visibility = View.GONE
        }
    }
}