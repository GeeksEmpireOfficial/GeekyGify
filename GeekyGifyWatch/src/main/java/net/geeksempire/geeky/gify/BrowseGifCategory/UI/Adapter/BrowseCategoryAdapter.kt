/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/12/20 5:55 PM
 * Last modified 2/12/20 5:45 PM
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
                            var  recyclerViewGifCategoryItemLongPress: RecyclerViewGifCategoryItemLongPress) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var categoryItemsData: ArrayList<CategoryItemData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (categoryItemsData[viewType].categoryLeft?.categoryTitle == context.getString(R.string.favoriteTitle)) {

            BrowseFavoriteListViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.browse_gif_category_favorite_item_view,
                    parent, false))
        } else {

            BrowseCategoryListViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.browse_gif_category_item_view,
                    parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

    override fun getItemCount(): Int {

        return categoryItemsData.size
    }

    override fun onBindViewHolder(viewHolderPayload: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(viewHolderPayload, position, payloads)

        if (categoryItemsData[position].categoryLeft?.categoryTitle == context.getString(R.string.favoriteTitle)) {

        } else {

            val viewHolder = viewHolderPayload as BrowseCategoryListViewHolder

            if (categoryItemsData[position].categoryLeft != null) {

                viewHolder.categoryIconLeft.visibility = View.VISIBLE
                viewHolder.categoryIconLeft.text = (categoryItemsData[position].categoryLeft?.categoryTitle)
            } else {
                viewHolder.categoryIconLeft.visibility = View.GONE
            }

            if (categoryItemsData[position].categoryRight != null) {

                viewHolder.categoryIconRight.visibility = View.VISIBLE
                viewHolder.categoryIconRight.text = (categoryItemsData[position].categoryRight?.categoryTitle)
            } else {
                viewHolder.categoryIconRight.visibility = View.GONE
            }
        }
    }

    override fun onBindViewHolder(initialViewHolder: RecyclerView.ViewHolder, position: Int) {

        if (categoryItemsData[position].categoryLeft?.categoryTitle == context.getString(R.string.favoriteTitle)) {

            val viewHolder = initialViewHolder as BrowseFavoriteListViewHolder

            viewHolder.favoriteIcon.visibility = View.VISIBLE

            viewHolder.favoriteIcon.text = (categoryItemsData[position].categoryLeft?.categoryTitle)

            val backgroundDrawableLayer = context.getDrawable(R.drawable.category_favorite_background) as LayerDrawable
            val backgroundDrawable = backgroundDrawableLayer.findDrawableByLayerId(R.id.drawableBackground)
            backgroundDrawable?.let {
                it.setTint(context.getColor(R.color.default_color_game_light))
                viewHolder.favoriteIcon.background = backgroundDrawableLayer
            }

            viewHolder.favoriteIcon.setOnClickListener {

                /*Intent(context, BrowseGifView::class.java).apply {
                    this.putExtra("CategoryName", categoryItemsData[position].categoryLeft?.categoryTitle.toString())
                    this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(this,
                        ActivityOptions.makeCustomAnimation(context, R.anim.slide_from_right, 0).toBundle())
                }*/
            }

        } else {

            val viewHolder = initialViewHolder as BrowseCategoryListViewHolder

            //Setup Left Item
            if (categoryItemsData[position].categoryLeft != null
                || !categoryItemsData[position].categoryLeft?.categoryTitle.isNullOrBlank()) {

                viewHolder.categoryIconLeft.visibility = View.VISIBLE

                viewHolder.categoryIconLeft.text = (categoryItemsData[position].categoryLeft?.categoryTitle)

                val backgroundDrawableLeftLayer = context.getDrawable(R.drawable.category_left_background) as LayerDrawable
                val backgroundDrawableLeft = backgroundDrawableLeftLayer.findDrawableByLayerId(R.id.drawableBackground)
                backgroundDrawableLeft?.let {
                    it.setTint(categoryItemsData[position].categoryLeft!!.backgroundColor)
                    viewHolder.categoryIconLeft.background = backgroundDrawableLeftLayer
                }
                viewHolder.categoryIconLeft.setOnClickListener {
                    Log.d("BrowseCategoryAdapter", categoryItemsData[position].categoryLeft?.categoryTitle.toString())

                    Intent(context, BrowseGifView::class.java).apply {
                        this.putExtra("CategoryName", categoryItemsData[position].categoryLeft?.categoryTitle.toString())
                        this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(this,
                            ActivityOptions.makeCustomAnimation(context, R.anim.slide_from_right, 0).toBundle())
                    }
                }
            } else {
                viewHolder.categoryIconLeft.visibility = View.GONE
            }

            //Setup Right Item
            if (categoryItemsData[position].categoryRight != null
                || !categoryItemsData[position].categoryRight?.categoryTitle.isNullOrBlank()) {

                viewHolder.categoryIconRight.visibility = View.VISIBLE

                viewHolder.categoryIconRight.text = (categoryItemsData[position].categoryRight?.categoryTitle)

                val backgroundDrawableRightLayer = context.getDrawable(R.drawable.category_right_background) as LayerDrawable
                val backgroundDrawableRight = backgroundDrawableRightLayer.findDrawableByLayerId(R.id.drawableBackground)
                backgroundDrawableRight?.let {
                    it.setTint(categoryItemsData[position].categoryRight!!.backgroundColor)
                    viewHolder.categoryIconRight.background = backgroundDrawableRightLayer
                }

                viewHolder.categoryIconRight.setOnClickListener {
                    Log.d("BrowseCategoryAdapter", categoryItemsData[position].categoryRight?.categoryTitle.toString())

                    Intent(context, BrowseGifView::class.java).apply {
                        this.putExtra("CategoryName", categoryItemsData[position].categoryRight?.categoryTitle.toString())
                        this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(this,
                            ActivityOptions.makeCustomAnimation(context, R.anim.slide_from_right, 0).toBundle())
                    }
                }
            } else {
                viewHolder.categoryIconRight.visibility = View.GONE
            }

            viewHolder.categoryIconRight.setOnLongClickListener { view ->
                categoryItemsData[position].categoryRight?.categoryTitle?.let {
                    recyclerViewGifCategoryItemLongPress.itemLongPressed(RecyclerViewRightLeftItem.RIGHT_ITEM,
                        it
                    )
                }

                false
            }

            viewHolder.categoryIconLeft.setOnLongClickListener { view ->
                categoryItemsData[position].categoryLeft?.categoryTitle?.let {
                    recyclerViewGifCategoryItemLongPress.itemLongPressed(RecyclerViewRightLeftItem.LEFT_ITEM,
                        it
                    )
                }

                false
            }
        }
    }
}