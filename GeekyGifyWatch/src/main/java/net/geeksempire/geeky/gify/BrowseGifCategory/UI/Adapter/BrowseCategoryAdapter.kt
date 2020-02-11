/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/10/20 5:43 PM
 * Last modified 2/10/20 5:38 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.geeksempire.geeky.gify.BrowseGif.UI.BrowseGifView
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.CategoryItemData
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.RecyclerViewRightLeftItem
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.UI.RecyclerViewGifCategoryItemLongPress


class BrowseCategoryAdapter(var context: Context,
                            var  recyclerViewGifCategoryItemLongPress: RecyclerViewGifCategoryItemLongPress) : RecyclerView.Adapter<BrowseCategoryListViewHolder>() {

    lateinit var categoryItemsData: ArrayList<CategoryItemData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseCategoryListViewHolder {

        return BrowseCategoryListViewHolder(LayoutInflater.from(context).inflate(R.layout.browse_gif_category_item_view, parent, false))
    }

    override fun getItemCount(): Int {

        return categoryItemsData.size
    }

    override fun onBindViewHolder(viewHolderPayload: BrowseCategoryListViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(viewHolderPayload, position, payloads)

        if (categoryItemsData[position].categoryLeft != null) {
            viewHolderPayload.categoryIconLeft.visibility = View.VISIBLE

            viewHolderPayload.categoryIconLeft.text = (categoryItemsData[position].categoryLeft?.categoryTitle)
        } else {
            viewHolderPayload.categoryIconLeft.visibility = View.GONE
        }

        if (categoryItemsData[position].categoryRight != null) {
            viewHolderPayload.categoryIconRight.visibility = View.VISIBLE

            viewHolderPayload.categoryIconRight.text = (categoryItemsData[position].categoryRight?.categoryTitle)
        } else {
            viewHolderPayload.categoryIconRight.visibility = View.GONE
        }
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
                        ActivityOptions.makeCustomAnimation(context, R.anim.slide_from_right, 0).toBundle())
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
                        ActivityOptions.makeCustomAnimation(context, R.anim.slide_from_right, 0).toBundle())
                }
            }
        } else {
            viewHolderBrowseCategoryList.categoryIconRight.visibility = View.GONE
        }

        viewHolderBrowseCategoryList.categoryIconRight.setOnLongClickListener { view ->
            categoryItemsData[position].categoryRight?.categoryTitle?.let {
                recyclerViewGifCategoryItemLongPress.itemLongPressed(RecyclerViewRightLeftItem.RIGHT_ITEM,
                    it
                )
            }

            false
        }

        viewHolderBrowseCategoryList.categoryIconLeft.setOnLongClickListener { view ->
            categoryItemsData[position].categoryLeft?.categoryTitle?.let {
                recyclerViewGifCategoryItemLongPress.itemLongPressed(RecyclerViewRightLeftItem.LEFT_ITEM,
                    it
                )
            }

            false
        }
    }
}