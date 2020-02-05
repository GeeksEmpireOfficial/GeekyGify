/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/4/20 3:21 PM
 * Last modified 2/4/20 3:14 PM
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

class CategoryAdapter(var context: Context, var categoryItemsData: ArrayList<CategoryItemData>) : RecyclerView.Adapter<CategoryListViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {

        return CategoryListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category_view, parent, false))
    }

    override fun getItemCount(): Int {

        return categoryItemsData.size
    }

    override fun onBindViewHolder(viewHolderCategoryList: CategoryListViewHolder, position: Int) {

        if (categoryItemsData[position].categoryLeft != null) {
            viewHolderCategoryList.categoryIconLeft.visibility = View.VISIBLE

            viewHolderCategoryList.categoryIconLeft.text = (categoryItemsData[position].categoryLeft?.categoryTitle)

            val backgroundDrawableLeftLayer = context.getDrawable(R.drawable.category_left_background) as LayerDrawable
            val backgroundDrawableLeft = backgroundDrawableLeftLayer.findDrawableByLayerId(R.id.drawableBackground)
            backgroundDrawableLeft?.let {
                it.setTint(colorsList.random())
                viewHolderCategoryList.categoryIconLeft.background = backgroundDrawableLeftLayer
            }
            viewHolderCategoryList.categoryIconLeft.setOnClickListener {
                Log.d("CategoryAdapter", categoryItemsData[position].categoryLeft?.categoryTitle)

            }
        } else {
            viewHolderCategoryList.categoryIconLeft.visibility = View.GONE
        }

        if (categoryItemsData[position].categoryRight != null) {
            viewHolderCategoryList.categoryIconRight.visibility = View.VISIBLE

            viewHolderCategoryList.categoryIconRight.text = (categoryItemsData[position].categoryRight?.categoryTitle)

            val backgroundDrawableRightLayer = context.getDrawable(R.drawable.category_right_background) as LayerDrawable
            val backgroundDrawableRight = backgroundDrawableRightLayer.findDrawableByLayerId(R.id.drawableBackground)
            backgroundDrawableRight?.let {
                it.setTint(colorsList.random())
                viewHolderCategoryList.categoryIconRight.background = backgroundDrawableRightLayer
            }

            viewHolderCategoryList.categoryIconRight.setOnClickListener {
                Log.d("CategoryAdapter", categoryItemsData[position].categoryRight?.categoryTitle)

            }
        } else {
            viewHolderCategoryList.categoryIconRight.visibility = View.GONE
        }
    }
}