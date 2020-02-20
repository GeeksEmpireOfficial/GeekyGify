/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/20/20 2:24 PM
 * Last modified 2/20/20 2:00 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.DataController.Extension

import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.null_data_controller.*
import net.geeksempire.geeky.gify.DataController.NullDataController
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.Converter.ConvertFile
import net.geeksempire.geeky.gify.Utils.UI.PopupAppShortcuts

fun NullDataController.setupClickNullDataControllerAdsApp() {

    val popupAppShortcuts: PopupAppShortcuts = PopupAppShortcuts(context!!)

    //Geeks Empire - Facebook Page
    Glide.with(context!!)
        .load("https://lh3.googleusercontent.com/_mOI9EcM700XeBDnSKVvVz1WTLVFxhnQzKXhYZ8y7hQDqALmQNnMtSJwTJLxMocge5A=w512-h512-n")
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .apply(RequestOptions.circleCropTransform())
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                e?.printStackTrace()

                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                resource?.let {
                    popupAppShortcuts.create(
                        context!!.getString(R.string.geeksEmpireName),
                        context!!.getString(R.string.geeksEmpireName),
                        Icon.createWithBitmap(ConvertFile().drawableToBitmap(resource)),
                        context!!.getString(R.string.geeksEmpireLinkFacebook)
                    )
                }

                return true
            }
        })
        .submit()

    //T. Rex Runner
    Glide.with(context!!)
        .load("https://lh3.googleusercontent.com/f5fZLzzYj-Dx2emwCcPBkyqBrpXge2UAQhgDAzXzONszuME0sAyqq4nGeRZCn61uZg=s512")
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .apply(RequestOptions.circleCropTransform())
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                e?.printStackTrace()

                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                resource?.let {
                    popupAppShortcuts.create(
                        context!!.getString(R.string.tRexRunnerName),
                        context!!.getString(R.string.tRexRunnerName),
                        Icon.createWithBitmap(ConvertFile().drawableToBitmap(resource)),
                        context!!.getString(R.string.tRexRunnerLink)
                    )
                }

                return true
            }
        })
        .submit()

    //Float It
    Glide.with(context!!)
        .load("https://lh3.googleusercontent.com/yS28x2BrP45C6mfVPP7rt4Zs84X18kBEZfNF3BsbY4IyUT8VAVtoPT-nNDD5B8fRsnuF=s512")
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .apply(RequestOptions.circleCropTransform())
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                e?.printStackTrace()

                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                resource?.let {
                    popupAppShortcuts.create(
                        context!!.getString(R.string.floatItName),
                        context!!.getString(R.string.floatItName),
                        Icon.createWithBitmap(ConvertFile().drawableToBitmap(resource)),
                        context!!.getString(R.string.floatItLink)
                    )
                }

                return true
            }
        })
        .into(floatItIcon)

    //Super Shortcuts
    Glide.with(context!!)
        .load("https://lh3.googleusercontent.com/AmmKC1aIYMA3rGjbtl0t3yketF3P_WN4JoPOylj0HFxs63QQ-7gvxg96A8ZPrs4oBKs=s512")
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .apply(RequestOptions.circleCropTransform())
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                e?.printStackTrace()

                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                resource?.let {
                    popupAppShortcuts.create(
                        context!!.getString(R.string.superShortcutsName),
                        context!!.getString(R.string.superShortcutsName),
                        Icon.createWithBitmap(ConvertFile().drawableToBitmap(resource)),
                        context!!.getString(R.string.superShortcutsLink)
                    )
                }

                return true
            }
        })
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