/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/7/20 3:48 PM
 * Last modified 2/7/20 2:34 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.UI.Adapter

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.browse_gif_list_view.*
import net.geeksempire.geeky.gify.BrowseGif.UI.Adapter.Data.BrowseGifItemData
import net.geeksempire.geeky.gify.BrowseGif.UI.GifViewer
import net.geeksempire.geeky.gify.R

class BrowseGifAdapter(var context: AppCompatActivity, var browseGifItemData: ArrayList<BrowseGifItemData>) : RecyclerView.Adapter<BrowseGifListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseGifListViewHolder {

        return BrowseGifListViewHolder(LayoutInflater.from(context).inflate(R.layout.browse_gif_item_view, parent, false))
    }

    override fun getItemCount(): Int {

        return browseGifItemData.size
    }

    override fun onBindViewHolder(viewHoldBrowseGifList: BrowseGifListViewHolder, position: Int) {

        viewHoldBrowseGifList.mainView.setBackgroundColor(Color.parseColor(browseGifItemData[position].backgroundColor))
        Glide.with(context)
              .asGif()
              .diskCacheStrategy(DiskCacheStrategy.DATA)
              .load(browseGifItemData[position].gifPreviewUrl)
              .into(viewHoldBrowseGifList.gifPreview)

        viewHoldBrowseGifList.gifPreview.setOnClickListener {
            context.fragmentGifViewer.visibility = View.VISIBLE

            val gifViewer: Fragment = GifViewer()
            gifViewer.arguments = Bundle().apply {
                putString("GIF_LINK", browseGifItemData[position].gifOriginalUri)
            }

            context.supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_from_right, 0)
                .replace(R.id.fragmentGifViewer, gifViewer, "GIF VIEWER")
                .commit()
        }
    }
}