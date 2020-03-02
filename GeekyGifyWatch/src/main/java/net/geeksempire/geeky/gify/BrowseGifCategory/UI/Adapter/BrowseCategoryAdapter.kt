/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/2/20 6:08 AM
 * Last modified 3/2/20 6:03 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.BrowseGif.UI.BrowseGifView
import net.geeksempire.geeky.gify.BrowseGif.ViewModel.BrowseGifViewModel
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.CategoryItemData
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.RecyclerViewRightLeftItem
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Utils.BrowseGifCategoryType
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Utils.browseGifCategoryTypeView
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.ViewHolders.*
import net.geeksempire.geeky.gify.BrowseGifCategory.Utils.RecyclerViewGifCategoryItemPress
import net.geeksempire.geeky.gify.GifFavorite.UI.FavoritesGifView
import net.geeksempire.geeky.gify.R

class BrowseCategoryAdapter(var context: Context,
                            var  recyclerViewGifCategoryItemPress: RecyclerViewGifCategoryItemPress) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val categoryItemsData: ArrayList<CategoryItemData> = ArrayList<CategoryItemData>()

    var deleteCounter: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return browseGifCategoryTypeView(parent, categoryItemsData[viewType].viewType)
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

    override fun getItemCount(): Int {

        return categoryItemsData.size
    }

    override fun onBindViewHolder(viewHolderPayload: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(viewHolderPayload, position, payloads)

        if (categoryItemsData[position].viewType == BrowseGifCategoryType.GIF_ITEM_FAVORITE
            && viewHolderPayload is BrowseFavoriteListViewHolder) {

        } else if (categoryItemsData[position].viewType == BrowseGifCategoryType.GIF_ITEM_SEARCH
            && viewHolderPayload is BrowseSearchListViewHolder) {

        } else if (categoryItemsData[position].viewType == BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_NEW
            && viewHolderPayload is BrowseAddListViewHolder) {

        } else if (categoryItemsData[position].viewType == BrowseGifCategoryType.GIF_ITEM_TRENDING
            && viewHolderPayload is BrowseTrendingListViewHolder) {

        } else if (categoryItemsData[position].viewType == BrowseGifCategoryType.GIF_ITEM_SOCIAL_MEDIA
            && viewHolderPayload is BrowseSocialMediaListViewHolder) {

        } else if (categoryItemsData[position].viewType == BrowseGifCategoryType.GIF_ITEM_CATEGORIES
            && viewHolderPayload is BrowseCategoryListViewHolder) {

            val viewHolder = viewHolderPayload as BrowseCategoryListViewHolder

            //Setup Left Item
            if (categoryItemsData[position].categoryLeft != null
                || !categoryItemsData[position].categoryLeft?.categoryTitle.isNullOrBlank()) {

                viewHolder.categoryIconLeft.visibility = View.VISIBLE
                viewHolder.deleteLeftCategory.visibility = View.VISIBLE

                viewHolder.categoryIconLeft.text = (categoryItemsData[position].categoryLeft?.categoryTitle)
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
            } else {
                viewHolder.categoryIconRight.visibility = View.GONE
                viewHolder.deleteRightCategory.visibility = View.GONE
            }
        }
    }

    override fun onBindViewHolder(initialViewHolder: RecyclerView.ViewHolder, position: Int) {

        if (categoryItemsData[position].viewType == BrowseGifCategoryType.GIF_ITEM_FAVORITE
            && initialViewHolder is BrowseFavoriteListViewHolder) {

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

        } else if (categoryItemsData[position].viewType == BrowseGifCategoryType.GIF_ITEM_SEARCH
            && initialViewHolder is BrowseSearchListViewHolder) {

            val viewHolder = initialViewHolder as BrowseSearchListViewHolder

            viewHolder.searchIcon.visibility = View.VISIBLE


            val animatable = context.getDrawable(R.drawable.animated_search_icon) as Animatable
            animatable.start()

            Glide.with(context)
                .load(animatable as Drawable)
                .into(viewHolder.searchIcon)

            viewHolder.searchIcon.setOnClickListener {

                categoryItemsData[position].categoryLeft?.categoryTitle?.let { itemTitle ->
                    recyclerViewGifCategoryItemPress.itemPressed(RecyclerViewRightLeftItem.LeftItem,
                        itemTitle,
                        BrowseGifCategoryType.GIF_ITEM_SEARCH)
                }
            }

        } else if (categoryItemsData[position].viewType == BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_NEW
            && initialViewHolder is BrowseAddListViewHolder) {

            val viewHolder = initialViewHolder as BrowseAddListViewHolder

            viewHolder.addIcon.visibility = View.VISIBLE
            Glide.with(context)
                .load(R.drawable.icon_plus)
                .into(viewHolder.addIcon)

            viewHolder.addIcon.setOnClickListener {

                categoryItemsData[position].categoryLeft?.categoryTitle?.let { itemTitle ->
                    recyclerViewGifCategoryItemPress.itemPressed(RecyclerViewRightLeftItem.LeftItem,
                        itemTitle,
                        BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_NEW)
                }
            }

        } else if (categoryItemsData[position].viewType == BrowseGifCategoryType.GIF_ITEM_TRENDING
            && initialViewHolder is BrowseTrendingListViewHolder) {

            val viewHolder = initialViewHolder as BrowseTrendingListViewHolder

            viewHolder.trendingBackground.visibility = View.VISIBLE
            viewHolder.trendingTitle.visibility = View.VISIBLE

            viewHolder.trendingTitle.text = context.getString(R.string.trending)

            viewHolder.trendingTitle.setOnClickListener {

                Intent(context, BrowseGifView::class.java).apply {
                    this.putExtra("QueryType", BrowseGifViewModel.QUERY_TYPE.QUERY_TREND)
                    this.putExtra("CategoryName", context.getString(R.string.trending))
                    this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(this,
                        ActivityOptions.makeCustomAnimation(context, R.anim.slide_from_right, 0).toBundle())
                }
            }

        } else if (categoryItemsData[position].viewType == BrowseGifCategoryType.GIF_ITEM_SOCIAL_MEDIA
            && initialViewHolder is BrowseSocialMediaListViewHolder) {

            val viewHolder = initialViewHolder as BrowseSocialMediaListViewHolder

            viewHolder.rateReview.setOnClickListener {

                recyclerViewGifCategoryItemPress
                    .itemPressed(RecyclerViewRightLeftItem.LeftItem,
                        null,
                        BrowseGifCategoryType.GIF_ITEM_SOCIAL_MEDIA)
            }

            viewHolder.facebookIcon.setOnClickListener {

                recyclerViewGifCategoryItemPress
                    .itemPressed(RecyclerViewRightLeftItem.RightItem,
                        null,
                        BrowseGifCategoryType.GIF_ITEM_SOCIAL_MEDIA)
            }
        } else if (categoryItemsData[position].viewType == BrowseGifCategoryType.GIF_ITEM_CATEGORIES
            && initialViewHolder is BrowseCategoryListViewHolder) {

            val viewHolder = initialViewHolder as BrowseCategoryListViewHolder

            //Setup Left Item
            if (categoryItemsData[position].categoryLeft != null
                && !categoryItemsData[position].categoryLeft!!.categoryTitle.isNullOrBlank()) {

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
                        this.putExtra("QueryType", BrowseGifViewModel.QUERY_TYPE.QUERY_SEARCH)
                        this.putExtra("CategoryName", categoryItemsData[position].categoryLeft?.categoryTitle.toString())
                        this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(this,
                            ActivityOptions.makeCustomAnimation(context, R.anim.slide_from_right, 0).toBundle())
                    }
                }

                viewHolder.deleteLeftCategory.setOnClickListener {

                    categoryItemsData[position].categoryLeft?.categoryTitle?.let { categoryName ->
                        CoroutineScope(Dispatchers.IO).launch {

                            recyclerViewGifCategoryItemPress.deleteCategory(
                                RecyclerViewRightLeftItem.LeftItem,
                                position,
                                categoryName
                            )
                        }

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
                && !categoryItemsData[position].categoryRight!!.categoryTitle.isNullOrBlank()) {

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
                        this.putExtra("QueryType", BrowseGifViewModel.QUERY_TYPE.QUERY_SEARCH)
                        this.putExtra("CategoryName", categoryItemsData[position].categoryRight?.categoryTitle.toString())
                        this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(this,
                            ActivityOptions.makeCustomAnimation(context, R.anim.slide_from_right, 0).toBundle())
                    }
                }

                viewHolder.deleteRightCategory.setOnClickListener {

                    categoryItemsData[position].categoryRight?.categoryTitle?.let { categoryName ->
                        CoroutineScope(Dispatchers.IO).launch {

                            recyclerViewGifCategoryItemPress.deleteCategory(
                                RecyclerViewRightLeftItem.RightItem,
                                position,
                                categoryName
                            )
                        }

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
                    recyclerViewGifCategoryItemPress.itemLongPressed(RecyclerViewRightLeftItem.RightItem,
                        it,
                        BrowseGifCategoryType.GIF_ITEM_CATEGORIES
                    )
                }

                false
            }

            viewHolder.categoryIconLeft.setOnLongClickListener { view ->
                categoryItemsData[position].categoryLeft?.categoryTitle?.let {
                    recyclerViewGifCategoryItemPress.itemLongPressed(RecyclerViewRightLeftItem.LeftItem,
                        it,
                        BrowseGifCategoryType.GIF_ITEM_CATEGORIES
                    )
                }

                false
            }
        }
    }
}