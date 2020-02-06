/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/6/20 11:25 AM
 * Last modified 2/6/20 11:20 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Adapter

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.geeksempire.geeky.gify.BrowseGif.BrowseGifView
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.Data.CategoryItemData
import net.geeksempire.geeky.gify.R

class BrowseCategoryAdapter(var context: Context, var categoryItemsData: ArrayList<CategoryItemData>) : RecyclerView.Adapter<BrowseCategoryListViewHolder>() {

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
                it.setTint(categoryItemsData[position].categoryLeft!!.backgroundColor)
                viewHolderBrowseCategoryList.categoryIconLeft.background = backgroundDrawableLeftLayer
            }
            viewHolderBrowseCategoryList.categoryIconLeft.setOnClickListener {
                Log.d("BrowseCategoryAdapter", categoryItemsData[position].categoryLeft?.categoryTitle.toString())

                Intent(context, BrowseGifView::class.java).apply {
                    this.putExtra("CategoryName", categoryItemsData[position].categoryLeft?.categoryTitle.toString())
                    this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(this,
                        ActivityOptions.makeCustomAnimation(context, android.R.anim.fade_in, android.R.anim.fade_out).toBundle())
                }
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
                it.setTint(categoryItemsData[position].categoryRight!!.backgroundColor)
                viewHolderBrowseCategoryList.categoryIconRight.background = backgroundDrawableRightLayer
            }

            viewHolderBrowseCategoryList.categoryIconRight.setOnClickListener {
                Log.d("BrowseCategoryAdapter", categoryItemsData[position].categoryRight?.categoryTitle.toString())

                Intent(context, BrowseGifView::class.java).apply {
                    this.putExtra("CategoryName", categoryItemsData[position].categoryRight?.categoryTitle.toString())
                    this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(this,
                        ActivityOptions.makeCustomAnimation(context, android.R.anim.fade_in, android.R.anim.fade_out).toBundle())
                }
            }
        } else {
            viewHolderBrowseCategoryList.categoryIconRight.visibility = View.GONE
        }
    }
}