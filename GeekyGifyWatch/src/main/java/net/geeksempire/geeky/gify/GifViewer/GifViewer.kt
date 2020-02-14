/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 3:50 PM
 * Last modified 2/13/20 3:45 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifViewer

import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
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
import net.geeksempire.geeky.gify.GifViewer.Utils.GifViewerFragmentStateListener
import net.geeksempire.geeky.gify.R


class GifViewer(private val gifViewerFragmentStateListener: GifViewerFragmentStateListener?) : Fragment() {

    lateinit var linkToGif: String
    lateinit var gifPreviewLink: String
    lateinit var gifLinkToDownload: String

    var gifUserName: String? = null
    var gifUserAvatarUrl: String? = null
    var gifUserIsVerified: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linkToGif = arguments?.getString(GiphyJsonDataStructure.DATA_URL) ?: "https://media.giphy.com/media/ZCemAxolHlLetaTqLh/giphy.gif"
        gifPreviewLink = arguments?.getString(GiphyJsonDataStructure.DATA_IMAGES_PREVIEW_GIF) ?: "https://media.giphy.com/media/ZCemAxolHlLetaTqLh/giphy.gif"
        gifLinkToDownload = arguments?.getString(GiphyJsonDataStructure.DATA_IMAGES_ORIGINAL) ?: "https://media.giphy.com/media/ZCemAxolHlLetaTqLh/giphy.gif"

        gifUserName = arguments?.getString(GiphyJsonDataStructure.DATA_USER_NAME)
        gifUserAvatarUrl = arguments?.getString(GiphyJsonDataStructure.DATA_USER_AVATAR_URL)
        gifUserIsVerified = arguments?.getBoolean(GiphyJsonDataStructure.DATA_USER_IS_VERIFIED)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val vmBuilder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(vmBuilder.build())
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

        gifViewerFragmentStateListener?.onFragmentDetach()

        activity!!.fragmentGifViewer!!.visibility = View.GONE
    }
}