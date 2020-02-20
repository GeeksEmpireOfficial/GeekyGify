/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/19/20 6:19 PM
 * Last modified 2/19/20 6:09 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.DataController.Extension

import android.content.Intent
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.null_data_controller.*
import net.geeksempire.geeky.gify.DataController.NullDataController
import net.geeksempire.geeky.gify.R

fun NullDataController.setupClickNullDataControllerAdsApp() {

    Glide.with(context!!)
        .load("https://lh3.googleusercontent.com/yS28x2BrP45C6mfVPP7rt4Zs84X18kBEZfNF3BsbY4IyUT8VAVtoPT-nNDD5B8fRsnuF=s512")
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .apply(RequestOptions.circleCropTransform())
        .into(floatItIcon)

    Glide.with(context!!)
        .load("https://lh3.googleusercontent.com/AmmKC1aIYMA3rGjbtl0t3yketF3P_WN4JoPOylj0HFxs63QQ-7gvxg96A8ZPrs4oBKs=s512")
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .apply(RequestOptions.circleCropTransform())
        .into(superShortcutsIcon)

    floatItIcon.setOnClickListener {
        Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.floatItLink))).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }
    floatItName.setOnClickListener {
        Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.floatItLink))).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }

    superShortcutsIcon.setOnClickListener {
        Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.superShortcutsLink))).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }
    superShortcutsName.setOnClickListener {
        Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.superShortcutsLink))).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }
}