/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 8:48 PM
 * Last modified 2/13/20 8:46 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.geeksempire.geeky.gify.BrowseGif.UI.BrowseGifView
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.CategoryItemData
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.RecyclerViewRightLeftItem
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Utils.BrowseGifCategoryType
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Utils.browseGifCategoryTypeView
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.ViewHolders.BrowseAddListViewHolder
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.ViewHolders.BrowseCategoryListViewHolder
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.ViewHolders.BrowseFavoriteListViewHolder
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.ViewHolders.BrowseSearchListViewHolder
import net.geeksempire.geeky.gify.BrowseGifCategory.Utils.RecyclerViewGifCategoryItemPress
import net.geeksempire.geeky.gify.GifFavorite.UI.FavoritesGifView
import net.geeksempire.geeky.gify.R

class BrowseCategoryAdapter(var context: Context,
                            var  recyclerViewGifCategoryItemPress: RecyclerViewGifCategoryItemPress
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var categoryItemsData: ArrayList<CategoryItemData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return categoryItemsData[viewType].categoryLeft?.categoryTitle?.let {
            browseGifCategoryTypeView(parent, it)
        }!!
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

    override fun getItemCount(): Int {

        return categoryItemsData.size
    }

    override fun onBindViewHolder(viewHolderPayload: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(viewHolderPayload, position, payloads)

        if (categoryItemsData[position].categoryLeft?.categoryTitle == BrowseGifCategoryType.GIF_ITEM_FAVORITE) {

        } else if (categoryItemsData[position].categoryLeft?.categoryTitle == BrowseGifCategoryType.GIF_ITEM_SEARCH) {

        } else if (categoryItemsData[position].categoryLeft?.categoryTitle == BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD) {

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

        if (categoryItemsData[position].categoryLeft?.categoryTitle == BrowseGifCategoryType.GIF_ITEM_FAVORITE) {

            try {
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

                    Intent(context, FavoritesGifView::class.java).apply {
                        this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(this,
                            ActivityOptions.makeCustomAnimation(context, R.anim.slide_from_right, 0).toBundle())
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else if (categoryItemsData[position].categoryLeft?.categoryTitle == BrowseGifCategoryType.GIF_ITEM_SEARCH) {

            try {

                val viewHolder = initialViewHolder as BrowseSearchListViewHolder

                viewHolder.searchIcon.visibility = View.VISIBLE
                viewHolder.searchIcon.setBackgroundColor(Color.TRANSPARENT)
                Glide.with(context)
                    .load(R.drawable.gph_ic_search_pink)
                    .into(viewHolder.searchIcon)

                viewHolder.searchIcon.setOnClickListener {

                    categoryItemsData[position].categoryLeft?.categoryTitle?.let { itemTitle ->
                        recyclerViewGifCategoryItemPress.itemPressed(RecyclerViewRightLeftItem.LEFT_ITEM,
                            itemTitle,
                            BrowseGifCategoryType.GIF_ITEM_SEARCH_TYPE)
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else if (categoryItemsData[position].categoryLeft?.categoryTitle == BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD) {

            try {

                val viewHolder = initialViewHolder as BrowseAddListViewHolder

                viewHolder.addIcon.visibility = View.VISIBLE
                viewHolder.addIcon.setBackgroundColor(Color.TRANSPARENT)
                Glide.with(context)
                    .load(R.drawable.icon_plus)
                    .into(viewHolder.addIcon)

                viewHolder.addIcon.setOnClickListener {

                    categoryItemsData[position].categoryLeft?.categoryTitle?.let { itemTitle ->
                        recyclerViewGifCategoryItemPress.itemPressed(RecyclerViewRightLeftItem.LEFT_ITEM,
                            itemTitle,
                            BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_TYPE)
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {

            try {
                val viewHolder = initialViewHolder as BrowseCategoryListViewHolder

                //Setup Left Item
                if (categoryItemsData[position].categoryLeft != null
                    || !categoryItemsData[position].categoryLeft?.categoryTitle.isNullOrBlank()) {

                    viewHolder.categoryIconLeft.visibility = View.VISIBLE
                    viewHolder.deleteLeftCategory.visibility = View.VISIBLE

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

                    viewHolder.deleteLeftCategory.setOnClickListener {

                        categoryItemsData[position].categoryLeft?.categoryTitle?.let { categoryName ->
                            recyclerViewGifCategoryItemPress.deleteCategory(
                                RecyclerViewRightLeftItem.RIGHT_ITEM,
                                position,
                                categoryName
                            )

                            val fadeOutAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out)

                            viewHolder.categoryIconLeft.visibility = View.GONE
                            viewHolder.categoryIconLeft.startAnimation(fadeOutAnimation)

                            viewHolder.deleteLeftCategory.visibility = View.GONE
                            viewHolder.deleteLeftCategory.startAnimation(fadeOutAnimation)
                        }

                    }
                } else {
                    viewHolder.categoryIconLeft.visibility = View.GONE
                    viewHolder.deleteLeftCategory.visibility = View.GONE
                }

                //Setup Right Item
                if (categoryItemsData[position].categoryRight != null
                    || !categoryItemsData[position].categoryRight?.categoryTitle.isNullOrBlank()) {

                    viewHolder.categoryIconRight.visibility = View.VISIBLE
                    viewHolder.deleteRightCategory.visibility = View.VISIBLE

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

                    viewHolder.deleteRightCategory.setOnClickListener {

                        categoryItemsData[position].categoryRight?.categoryTitle?.let { categoryName ->
                            recyclerViewGifCategoryItemPress.deleteCategory(
                                RecyclerViewRightLeftItem.RIGHT_ITEM,
                                position,
                                categoryName
                            )

                            val fadeOutAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out)

                            viewHolder.categoryIconRight.visibility = View.GONE
                            viewHolder.categoryIconRight.startAnimation(fadeOutAnimation)

                            viewHolder.deleteRightCategory.visibility = View.GONE
                            viewHolder.deleteRightCategory.startAnimation(fadeOutAnimation)
                        }

                    }
                } else {
                    viewHolder.categoryIconRight.visibility = View.GONE
                    viewHolder.deleteRightCategory.visibility = View.GONE
                }

                viewHolder.categoryIconRight.setOnLongClickListener { view ->
                    categoryItemsData[position].categoryRight?.categoryTitle?.let {
                        recyclerViewGifCategoryItemPress.itemLongPressed(RecyclerViewRightLeftItem.RIGHT_ITEM,
                            it,
                            BrowseGifCategoryType.GIF_ITEM_CATEGORIES_TYPE
                        )
                    }

                    false
                }

                viewHolder.categoryIconLeft.setOnLongClickListener { view ->
                    categoryItemsData[position].categoryLeft?.categoryTitle?.let {
                        recyclerViewGifCategoryItemPress.itemLongPressed(RecyclerViewRightLeftItem.LEFT_ITEM,
                            it,
                            BrowseGifCategoryType.GIF_ITEM_CATEGORIES_TYPE
                        )
                    }

                    false
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}