/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/10/20 5:18 PM
 * Last modified 2/10/20 4:51 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifViewer

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import kotlinx.android.synthetic.main.browse_gif_list_view.*
import kotlinx.android.synthetic.main.gif_view.*
import kotlinx.android.synthetic.main.gif_view.view.*
import net.geeksempire.geeky.gify.BrowseGif.Data.GiphyJsonDataStructure
import net.geeksempire.geeky.gify.GifViewer.Extension.setupGifViewClickListener
import net.geeksempire.geeky.gify.GifViewer.Extension.setupUserProfileInformation
import net.geeksempire.geeky.gify.R


class GifViewer : Fragment() {

    lateinit var gifLinkToDownload: String

    var gifUserName: String? = null
    var gifUserAvatarUrl: String? = null
    var gifUserIsVerified: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gifLinkToDownload = arguments?.getString(GiphyJsonDataStructure.DATA_IMAGES_ORIGINAL) ?: "https://media.giphy.com/media/ZCemAxolHlLetaTqLh/giphy.gif"

        gifUserName = arguments?.getString(GiphyJsonDataStructure.DATA_USER_NAME)
        gifUserAvatarUrl = arguments?.getString(GiphyJsonDataStructure.DATA_USER_AVATAR_URL)
        gifUserIsVerified = arguments?.getBoolean(GiphyJsonDataStructure.DATA_USER_IS_VERIFIED)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.gif_view, container, false)

        Glide.with(context!!)
            .asGif()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load(R.drawable.gradient_loading)
            .into(view.progressBarGifView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(context!!)
            .asGif()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load(gifLinkToDownload)
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(glideException: GlideException?, any: Any?, target: com.bumptech.glide.request.target.Target<GifDrawable>?, boolean: Boolean): Boolean {

                    return false
                }

                override fun onResourceReady(drawable: GifDrawable?, any: Any?, target: com.bumptech.glide.request.target.Target<GifDrawable>?, dataSource: DataSource?, boolean: Boolean): Boolean {
                    Handler().postDelayed({
                        progressBarGifView.visibility = View.GONE

                        setupGifViewClickListener()
                    }, 500)

                    return false
                }
            })
            .into(gifView)

        setupUserProfileInformation()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        activity!!.fragmentGifViewer!!.visibility = View.GONE
    }
}